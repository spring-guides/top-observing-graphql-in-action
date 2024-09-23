package io.spring.guides.graphqlmusic.support;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Locale;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.IntValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import static graphql.scalars.util.Kit.typeName;


public final class DurationSecondsScalar {

    public static final GraphQLScalarType INSTANCE;

    private DurationSecondsScalar() {
    }

    static {
        Coercing<Duration, Long> coercing = new Coercing<Duration, Long>() {
            @Override
            public Long serialize(Object input, GraphQLContext graphQLContext, Locale locale) throws CoercingSerializeException {
                if (input instanceof Duration duration) {
                    return duration.toSeconds();
                } else if (input instanceof String aString) {
                    return Duration.ofSeconds(Long.parseLong(aString)).toSeconds();
                } else {
                    throw new CoercingSerializeException(
                            "Expected a 'Long' or 'java.time.Duration' but was '" + typeName(input) + "'."
                    );
                }
            }

            @Override
            public Duration parseValue(Object input, GraphQLContext graphQLContext, Locale locale) throws CoercingParseValueException {
                if (input instanceof Duration duration) {
                    return duration;
                } else if (input instanceof String aString) {
                    return Duration.ofSeconds(Long.parseLong(aString));
                } else {
                    throw new CoercingParseValueException(
                            "Expected a 'Long' or 'java.time.Duration' but was '" + typeName(input) + "'."
                    );
                }
            }

            @Override
            public Duration parseLiteral(Value<?> input, CoercedVariables coercedVariables,
                                         GraphQLContext graphQLContext, Locale locale) throws CoercingParseLiteralException {
                if (input instanceof IntValue intValue) {
                    return Duration.ofSeconds(intValue.getValue().longValue());
                }
                throw new CoercingParseLiteralException(
                        "Expected AST type 'StringValue' but was '" + typeName(input) + "'."
                );
            }

            @Override
            public IntValue valueToLiteral(Object input, GraphQLContext graphQLContext, Locale locale) {
                Long aLong = serialize(input, graphQLContext, locale);
                return IntValue.newIntValue(BigInteger.valueOf(aLong)).build();
            }

        };

        INSTANCE = GraphQLScalarType.newScalar()
                .name("Duration")
                .description("A duration in seconds")
                .coercing(coercing)
                .build();
    }

}
