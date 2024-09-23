package io.spring.guides.graphqlmusic.tracks;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackRepository extends MongoRepository<Track, String> {

	List<Track> findByAlbumIdOrderByNumber(String albumId);

	Window<Track> findByIdInOrderByTitle(Set<String> trackIds, ScrollPosition scrollPosition, Limit limit);

}