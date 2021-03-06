package com.graphql_java_generator.client.domain.forum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.graphql_java_generator.annotation.GraphQLNonScalar;
import com.graphql_java_generator.annotation.GraphQLQuery;
import com.graphql_java_generator.client.QueryExecutor;
import com.graphql_java_generator.client.QueryExecutorImpl;
import com.graphql_java_generator.client.request.Builder;
import com.graphql_java_generator.client.request.InputParameter;
import com.graphql_java_generator.client.request.ObjectResponse;
import com.graphql_java_generator.client.response.GraphQLExecutionException;
import com.graphql_java_generator.client.response.GraphQLRequestPreparationException;

/**
 * @author generated by graphql-java-generator
 * @See https://github.com/graphql-java-generator/graphql-java-generator
 */
public class MutationType {

	/** Logger for this class */
	private static Logger logger = LogManager.getLogger();

	final QueryExecutor executor;
	
	/**
	 * This constructor expects the URI of the GraphQL server.<BR/>
	 * For example: https://my.server.com/graphql
	 * 
	 * @param graphqlEndpoint
	 */
	public MutationType(String graphqlEndpoint) {
		this.executor = new QueryExecutorImpl(graphqlEndpoint);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writting the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Board}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look at
	 * the StarWars, Forum and other samples for more complex queries.
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
	 * @param episode
	 *            Parameter 1 of this query
	 * @throws IOException
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Board.class)
	@GraphQLQuery
	public Board createBoard(String queryResponseDef, String name, Boolean publiclyAvailable)
			throws GraphQLExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing of query 'createBoard' in query mode: {} ", queryResponseDef);
		ObjectResponse objectResponse = getCreateBoardResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return createBoard(objectResponse, name, publiclyAvailable);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param episode
	 * @throws IOException
	 * @throws GraphQLExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Board.class)
	@GraphQLQuery
	public Board createBoard(ObjectResponse objectResponse, String name, Boolean publiclyAvailable) 
			throws GraphQLExecutionException  {
		if (logger.isTraceEnabled()) {
			logger.trace("Executing of mutation 'createBoard' with parameters: {}, {} ", name, publiclyAvailable);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing of mutation 'createBoard'");
		}
	
		// InputParameters
		List<InputParameter> parameters = new ArrayList<>();
		parameters.add(new InputParameter("name", name));
		parameters.add(new InputParameter("publiclyAvailable", publiclyAvailable));

		if (!Board.class.equals(objectResponse.getFieldClass())) {
			throw new GraphQLExecutionException("The ObjectResponse parameter should be an instance of "
					+ Board.class + ", but is an instance of " + objectResponse.getClass().getName());
		}

		MutationTypeCreateBoard ret = executor.execute("mutation", objectResponse, parameters, MutationTypeCreateBoard.class);
		
		return ret.createBoard;
	}

	/**
	 * Get the {@link ObjectResponse.Builder} for the Board, as expected by the createBoard query.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public Builder getCreateBoardResponseBuilder() throws GraphQLRequestPreparationException {
		return ObjectResponse.newQueryResponseDefBuilder(getClass(), "createBoard");
	}
	
	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writting the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Topic}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look at
	 * the StarWars, Forum and other samples for more complex queries.
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
	 * @param episode
	 *            Parameter 1 of this query
	 * @throws IOException
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Topic.class)
	@GraphQLQuery
	public Topic createTopic(String queryResponseDef, String authorId, Boolean publiclyAvailable, String title, String content)
			throws GraphQLExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing of query 'createTopic' in query mode: {} ", queryResponseDef);
		ObjectResponse objectResponse = getCreateTopicResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return createTopic(objectResponse, authorId, publiclyAvailable, title, content);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param episode
	 * @throws IOException
	 * @throws GraphQLExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Topic.class)
	@GraphQLQuery
	public Topic createTopic(ObjectResponse objectResponse, String authorId, Boolean publiclyAvailable, String title, String content) 
			throws GraphQLExecutionException  {
		if (logger.isTraceEnabled()) {
			logger.trace("Executing of mutation 'createTopic' with parameters: {}, {}, {}, {} ", authorId, publiclyAvailable, title, content);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing of mutation 'createTopic'");
		}
	
		// InputParameters
		List<InputParameter> parameters = new ArrayList<>();
		parameters.add(new InputParameter("authorId", authorId));
		parameters.add(new InputParameter("publiclyAvailable", publiclyAvailable));
		parameters.add(new InputParameter("title", title));
		parameters.add(new InputParameter("content", content));

		if (!Topic.class.equals(objectResponse.getFieldClass())) {
			throw new GraphQLExecutionException("The ObjectResponse parameter should be an instance of "
					+ Topic.class + ", but is an instance of " + objectResponse.getClass().getName());
		}

		MutationTypeCreateTopic ret = executor.execute("mutation", objectResponse, parameters, MutationTypeCreateTopic.class);
		
		return ret.createTopic;
	}

	/**
	 * Get the {@link ObjectResponse.Builder} for the Topic, as expected by the createTopic query.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public Builder getCreateTopicResponseBuilder() throws GraphQLRequestPreparationException {
		return ObjectResponse.newQueryResponseDefBuilder(getClass(), "createTopic");
	}
	
	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writting the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Post}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look at
	 * the StarWars, Forum and other samples for more complex queries.
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
	 * @param episode
	 *            Parameter 1 of this query
	 * @throws IOException
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Post.class)
	@GraphQLQuery
	public Post createPost(String queryResponseDef, String authorId, Boolean publiclyAvailable, String title, String content)
			throws GraphQLExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing of query 'createPost' in query mode: {} ", queryResponseDef);
		ObjectResponse objectResponse = getCreatePostResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return createPost(objectResponse, authorId, publiclyAvailable, title, content);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param episode
	 * @throws IOException
	 * @throws GraphQLExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Post.class)
	@GraphQLQuery
	public Post createPost(ObjectResponse objectResponse, String authorId, Boolean publiclyAvailable, String title, String content) 
			throws GraphQLExecutionException  {
		if (logger.isTraceEnabled()) {
			logger.trace("Executing of mutation 'createPost' with parameters: {}, {}, {}, {} ", authorId, publiclyAvailable, title, content);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing of mutation 'createPost'");
		}
	
		// InputParameters
		List<InputParameter> parameters = new ArrayList<>();
		parameters.add(new InputParameter("authorId", authorId));
		parameters.add(new InputParameter("publiclyAvailable", publiclyAvailable));
		parameters.add(new InputParameter("title", title));
		parameters.add(new InputParameter("content", content));

		if (!Post.class.equals(objectResponse.getFieldClass())) {
			throw new GraphQLExecutionException("The ObjectResponse parameter should be an instance of "
					+ Post.class + ", but is an instance of " + objectResponse.getClass().getName());
		}

		MutationTypeCreatePost ret = executor.execute("mutation", objectResponse, parameters, MutationTypeCreatePost.class);
		
		return ret.createPost;
	}

	/**
	 * Get the {@link ObjectResponse.Builder} for the Post, as expected by the createPost query.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public Builder getCreatePostResponseBuilder() throws GraphQLRequestPreparationException {
		return ObjectResponse.newQueryResponseDefBuilder(getClass(), "createPost");
	}
	
}
