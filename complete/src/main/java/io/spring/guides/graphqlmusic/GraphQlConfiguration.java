package io.spring.guides.graphqlmusic;

import graphql.scalars.ExtendedScalars;
import io.spring.guides.graphqlmusic.support.DurationSecondsScalar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfiguration {

	@Bean
	public RuntimeWiringConfigurer runtimeWiringConfigurer() {
		return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.Date)
				.scalar(ExtendedScalars.Url)
				.scalar(DurationSecondsScalar.INSTANCE);
	}

}
