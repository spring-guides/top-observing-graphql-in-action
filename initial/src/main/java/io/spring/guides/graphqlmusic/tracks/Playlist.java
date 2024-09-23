package io.spring.guides.graphqlmusic.tracks;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document
@CompoundIndex(def = "{'name': 1, 'author': 1}", unique = true)
public class Playlist {

    private String id;

    private final String name;

    private final String author;

    private Set<String> trackIds;

    public Playlist(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getTrackIds() {
        return trackIds;
    }

    public void setTrackIds(Set<String> trackIds) {
        this.trackIds = trackIds;
    }

    public void addTrack(Track track) {
        if (this.trackIds == null) {
            this.trackIds = new HashSet<>();
        }
        this.trackIds.add(track.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(id, playlist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
