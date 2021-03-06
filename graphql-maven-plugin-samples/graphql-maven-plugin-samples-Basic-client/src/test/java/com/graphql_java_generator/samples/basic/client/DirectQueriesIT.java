package com.graphql_java_generator.samples.basic.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.generated.graphql.Query;
import com.graphql_java_generator.client.response.GraphQLExecutionException;
import com.graphql_java_generator.client.response.GraphQLRequestPreparationException;

/**
 * As it is suffixed by "IT", this is an integration test. Thus, it allows us to start the GraphQL StatWars server, see
 * the pom.xml file for details.
 * 
 * @author EtienneSF
 */
class DirectQueriesIT {

	Query query;

	@BeforeEach
	void setUp() throws Exception {
		query = new Query("http://localhost:8180/graphql");
	}

	@Test
	void hello() throws GraphQLExecutionException, GraphQLRequestPreparationException {
		assertEquals("Hello World", query.hello("", "World"));
	}

}
