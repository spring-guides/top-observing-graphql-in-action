package io.spring.guides.graphqlmusic.tracks;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Controller
public class TracksController {

	private final MongoTemplate mongoTemplate;

	private final TrackRepository trackRepository;

	public TracksController(MongoTemplate mongoTemplate, TrackRepository trackRepository) {
		this.mongoTemplate = mongoTemplate;
		this.trackRepository = trackRepository;
	}

	@QueryMapping
	public Optional<Album> album(@Argument String id) {
		return this.mongoTemplate.query(Album.class)
				.matching(query(where("id").is(id)))
				.first();
	}

	@SchemaMapping
	public List<Track> tracks(Album album) {
		return this.trackRepository.findByAlbumIdOrderByNumber(album.getId());
	}
}
