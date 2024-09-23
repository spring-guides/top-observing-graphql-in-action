package io.spring.guides.graphqlmusic;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("music")
public class MusicProperties {

    private Lyrics lyrics = new Lyrics();

    public Lyrics getLyrics() {
        return this.lyrics;
    }

    public static class Lyrics {

        private Duration delay = Duration.ofSeconds(2);

        public Duration getDelay() {
            return this.delay;
        }

        public void setDelay(Duration delay) {
            this.delay = delay;
        }
    }
}