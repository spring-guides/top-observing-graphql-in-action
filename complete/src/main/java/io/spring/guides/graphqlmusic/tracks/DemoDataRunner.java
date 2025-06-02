package io.spring.guides.graphqlmusic.tracks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import io.spring.guides.graphqlmusic.faker.DemoDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.stereotype.Component;

@Component
public class DemoDataRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DemoDataRunner.class);

    private final MongoTemplate mongoTemplate;

    private final DemoDataGenerator demoDataGenerator;

    public DemoDataRunner(MongoTemplate mongoTemplate, DemoDataGenerator demoDataGenerator) {
        this.mongoTemplate = mongoTemplate;
        this.demoDataGenerator = demoDataGenerator;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List.of(Track.class, Artist.class, Album.class, Playlist.class)
                .forEach(this.mongoTemplate::dropCollection);
        createMongoDbIndexFor(Track.class);
        createMongoDbIndexFor(Playlist.class);

        int artistCount = 5;
        int albumsPerArtist = 3;
        List<Album> albums = new ArrayList<>();
        for (int i = 0; i < artistCount; i++) {
            Artist newArtist = this.mongoTemplate.save(this.demoDataGenerator.createArtist());
            for (int j = 0; j < albumsPerArtist; j++) {
                Album newAlbum = this.mongoTemplate.insert(this.demoDataGenerator.createAlbum(Set.of(newArtist)));
                Collection<Track> newTracks = this.mongoTemplate.insertAll(this.demoDataGenerator.createTracks(newAlbum));
                newAlbum.addTracks(newTracks);
                newAlbum = this.mongoTemplate.save(newAlbum);
                logger.info(newAlbum.toString());
                albums.add(newAlbum);
            }
        }

        List<Playlist> playlists = new ArrayList<>();
        playlists.add(this.demoDataGenerator.createFavoritePlaylist("rstoyanchev", albums));
        playlists.add(this.demoDataGenerator.createFavoritePlaylist("bclozel", albums));
        this.mongoTemplate.insertAll(playlists)
                .forEach(playlist -> logger.info(playlist.toString()));
    }

    private void createMongoDbIndexFor(Class<?> entityClass) {
        MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext = this.mongoTemplate
                .getConverter().getMappingContext();
        IndexResolver resolver = new MongoPersistentEntityIndexResolver(mappingContext);
        IndexOperations indexOperations = mongoTemplate.indexOps(entityClass);
        resolver.resolveIndexFor(entityClass).forEach(indexOperations::createIndex);
    }
}
