package io.spring.guides.graphqlmusic.tracks;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.Set;

@Controller
public class PlaylistController {

	private final PlaylistRepository playlistRepository;

	private final TrackRepository trackRepository;

	public PlaylistController(PlaylistRepository playlistRepository, TrackRepository trackRepository) {
		this.playlistRepository = playlistRepository;
		this.trackRepository = trackRepository;
	}

	@QueryMapping
	public Optional<Playlist> favoritePlaylist(@Argument String authorName) {
		return this.playlistRepository.findByAuthorAndNameEquals(authorName, "Favorites");
	}

	@SchemaMapping
	Window<Track> tracks(Playlist playlist, ScrollSubrange subrange) {
		Set<String> trackIds = playlist.getTrackIds();
		ScrollPosition scrollPosition = subrange.position().orElse(ScrollPosition.offset());
		Limit limit = Limit.of(subrange.count().orElse(10));
		return this.trackRepository.findByIdInOrderByTitle(trackIds, scrollPosition, limit);
	}

}