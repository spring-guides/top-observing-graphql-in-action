package io.spring.guides.graphqlmusic.tracks;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface TrackRepository extends MongoRepository<Track, String> {

}
