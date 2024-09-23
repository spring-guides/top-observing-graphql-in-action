package io.spring.guides.graphqlmusic.tracks;


import io.spring.guides.graphqlmusic.GraphQlConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.Optional;

@GraphQlTest(controllers = PlaylistController.class)
@Import(GraphQlConfiguration.class)
class PlaylistControllerTests {

	@Autowired
	private GraphQlTester graphQlTester;

	@MockBean
	private PlaylistRepository playlistRepository;

	@Test
	void shouldReplyWithFavoritePlaylist() {
		Playlist favorites = new Playlist("Favorites", "bclozel");
		favorites.setId("favorites");

		BDDMockito.when(playlistRepository.findByAuthorAndNameEquals("bclozel", "Favorites")).thenReturn(Optional.of(favorites));

		graphQlTester.document("""
                  {
                    favoritePlaylist(authorName: "bclozel") {
                      id
                      name
                      author
                    }
                  }
                  """)
				.execute()
				.path("favoritePlaylist.name").entity(String.class).isEqualTo("Favorites");
	}

}