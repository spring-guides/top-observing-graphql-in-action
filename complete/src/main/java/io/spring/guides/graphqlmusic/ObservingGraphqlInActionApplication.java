package io.spring.guides.graphqlmusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MusicProperties.class)
public class ObservingGraphqlInActionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObservingGraphqlInActionApplication.class, args);
	}

}
