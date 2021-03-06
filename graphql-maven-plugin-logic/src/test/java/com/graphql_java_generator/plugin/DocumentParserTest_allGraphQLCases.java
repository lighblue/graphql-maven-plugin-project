package com.graphql_java_generator.plugin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.graphql_java_generator.plugin.DocumentParser;
import com.graphql_java_generator.plugin.PluginConfiguration;
import com.graphql_java_generator.plugin.language.Field;
import com.graphql_java_generator.plugin.language.Type;
import com.graphql_java_generator.plugin.language.impl.EnumType;
import com.graphql_java_generator.plugin.language.impl.ObjectType;

import graphql.language.Definition;
import graphql.language.Document;
import graphql.language.EnumTypeDefinition;
import graphql.language.ObjectTypeDefinition;
import graphql.language.SchemaDefinition;
import graphql.mavenplugin_notscannedbyspring.AllGraphQLCases_Server_SpringConfiguration;

/**
 * 
 * @author EtienneSF
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AllGraphQLCases_Server_SpringConfiguration.class })
class DocumentParserTest_allGraphQLCases {

	@Resource
	private DocumentParser documentParser;

	@Resource
	private PluginConfiguration pluginConfiguration;

	@Resource
	List<Document> documents;

	@BeforeEach
	void setUp() throws Exception {
		//
	}

	@Test
	@DirtiesContext
	void test_parseOneDocument_allGraphQLCases() {
		// Go, go, go
		int i = documentParser.parseDocuments();

		// Verification
		assertEquals(13, i, "Nb classes are generated");
		assertEquals(6, documentParser.objectTypes.size(), "Nb objects");
		assertEquals(3, documentParser.interfaceTypes.size(), "Nb interfaces");
		assertEquals(1, documentParser.enumTypes.size(), "Nb enums");
		assertEquals(1, documentParser.queryTypes.size(), "Nb queries");
		assertEquals(1, documentParser.mutationTypes.size(), "Nb mutations");
		assertEquals(1, documentParser.subscriptionTypes.size(), "Nb subscriptions");

		assertEquals("query", documentParser.queryTypes.get(0).getRequestType());
		assertEquals("mutation", documentParser.mutationTypes.get(0).getRequestType());
		assertEquals("subscription", documentParser.subscriptionTypes.get(0).getRequestType());
	}

	@Test
	@DirtiesContext
	void test_addObjectType_noImplement() {
		// Preparation
		String objectName = "allFieldCases";
		ObjectTypeDefinition def = null;
		for (Definition<?> node : documents.get(0).getDefinitions()) {
			if (node instanceof ObjectTypeDefinition && ((ObjectTypeDefinition) node).getName().equals(objectName)) {
				def = (ObjectTypeDefinition) node;
			}
		} // for
		assertNotNull(def, "We should have found our test case (" + objectName + ")");
		// We need to parse the whole document, to get the types map filled.
		documentParser.parseDocuments();
		// To be sure to properly find our parsed object type, we empty the documentParser objects list.
		documentParser.objectTypes = new ArrayList<>();

		// Go, go, go
		ObjectType type = documentParser.readObjectType(def);

		// Verification
		assertEquals(objectName, type.getName(), "Checks the name");
		assertEquals(0, type.getImplementz().size(), "No implementation");
		assertEquals(10, type.getFields().size(), "Number of fields");

		int j = 0; // The first field is 0, see ++j below

		// checkField(field, fieldDescForJUnitMessage, name, list, mandatory, itemMandatory, typeName, clazz)
		// id: ID!
		checkField(type, j++, "id", false, true, null, "UUID", UUID.class.getName());
		// name: String!
		checkField(type, j++, "name", false, true, null, "String", String.class.getName());
		// forname: String
		checkField(type, j++, "forname", false, false, null, "String", String.class.getName());
		// age: int!
		checkField(type, j++, "age", false, true, null, "int", Integer.class.getName());
		// nbComments: int
		checkField(type, j++, "nbComments", false, false, null, "int", Integer.class.getName());
		// comments: [String]
		checkField(type, j++, "comments", true, false, false, "String", String.class.getName());
		// booleans: [boolean!]
		checkField(type, j++, "booleans", true, false, true, "boolean", Boolean.class.getName());
		// aliases: [String]!
		checkField(type, j++, "aliases", true, true, false, "String", String.class.getName());
		// planets: [String!]!
		checkField(type, j++, "planets", true, true, true, "String", String.class.getName());
		// friends: [Human!]
		checkField(type, j++, "friends", true, false, true, "Human", pluginConfiguration.getPackageName() + ".Human");
	}

	@Test
	@DirtiesContext
	void test_addObjectType_withImplement() {
		// Preparation
		String objectName = "Human";
		ObjectTypeDefinition def = null;
		for (Definition<?> node : documents.get(0).getDefinitions()) {
			if (node instanceof ObjectTypeDefinition && ((ObjectTypeDefinition) node).getName().equals(objectName)) {
				def = (ObjectTypeDefinition) node;
			}
		} // for
		assertNotNull(def, "We should have found our test case (" + objectName + ")");
		// We need to parse the whole document, to get the types map filled.
		documentParser.parseDocuments();
		// To be sure to properly find our parsed object type, we empty the documentParser objects list.
		documentParser.objectTypes = new ArrayList<>();

		// Go, go, go
		ObjectType type = documentParser.readObjectType(def);

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Verification
		assertEquals(objectName, type.getName(), "Checks the name");

		// Implementation
		assertEquals(2, type.getImplementz().size(), "Two implementations");
		assertEquals("Character", type.getImplementz().get(0), "First implementation");
		assertEquals("Commented", type.getImplementz().get(1), "Second implementation");

		// Field
		assertEquals(8, type.getFields().size(), "Number of fields");

		int j = 0; // The first field is 0, see ++j below

		// checkField(field, fieldDescForJUnitMessage, name, list, mandatory, itemMandatory, typeName, clazz)
		// id: ID!
		checkField(type, j++, "id", false, true, null, "UUID", UUID.class.getName());
		// name: String!
		checkField(type, j++, "name", false, true, null, "String", String.class.getName());
		// bestFriend: Character
		checkField(type, j++, "bestFriend", false, false, null, "Character",
				pluginConfiguration.getPackageName() + ".Character");
		// friends: [Character]
		checkField(type, j++, "friends", true, false, false, "Character",
				pluginConfiguration.getPackageName() + ".Character");
		// nbComments: int
		checkField(type, j++, "nbComments", false, false, null, "int", Integer.class.getName());
		// comments: [String]
		checkField(type, j++, "comments", true, false, false, "String", String.class.getName());
		// appearsIn: [Episode]!
		checkField(type, j++, "appearsIn", true, true, false, "Episode",
				pluginConfiguration.getPackageName() + ".Episode");
		// homePlanet: String
		checkField(type, j++, "homePlanet", false, false, null, "String", String.class.getName());
	}

	@Test
	@DirtiesContext
	void test_readSchemaDefinition() {
		// Preparation
		List<String> queries = new ArrayList<>();
		List<String> mutations = new ArrayList<>();
		List<String> subscriptions = new ArrayList<>();
		String objectName = "schema";
		SchemaDefinition schema = null;
		for (Definition<?> node : documents.get(0).getDefinitions()) {
			if (node instanceof SchemaDefinition) {
				schema = (SchemaDefinition) node;
				break;
			}
		} // for
		assertNotNull(schema, "We should have found our test case (" + objectName + ")");
		// To be sure to properly find our parsed object type, we empty the documentParser objects list.
		documentParser.objectTypes = new ArrayList<>();

		// Go, go, go
		documentParser.readSchemaDefinition(schema, queries, mutations, subscriptions);

		// Verification
		assertEquals(1, queries.size(), "Nb queries");
		assertEquals("MyQueryType", queries.get(0), "the query");

		assertEquals(1, mutations.size(), "Nb mutations");
		assertEquals("AnotherMutationType", mutations.get(0), "the mutation");

		assertEquals(1, subscriptions.size(), "Nb subscriptions");
		assertEquals("TheSubscriptionType", subscriptions.get(0), "the subscription");
	}

	@Test
	@DirtiesContext
	void test_readObjectType_QueryType() {
		// Preparation
		String objectName = "MyQueryType";
		ObjectTypeDefinition def = null;
		for (Definition<?> node : documents.get(0).getDefinitions()) {
			if (node instanceof ObjectTypeDefinition && ((ObjectTypeDefinition) node).getName().equals(objectName)) {
				def = (ObjectTypeDefinition) node;
			}
		} // for
		assertNotNull(def, "We should have found our test case (" + objectName + ")");
		// We need to parse the whole document, to get the types map filled.
		documentParser.parseDocuments();
		// To be sure to properly find our parsed object type, we empty the documentParser objects list.
		documentParser.queryTypes = new ArrayList<>();

		// Go, go, go
		ObjectType type = documentParser.readObjectType(def);

		// Verification
		assertEquals("MyQueryType", type.getName());
		assertEquals(7, type.getFields().size());

		int j = 0; // The first query is 0, see ++j below

		// Each query is actually a field. So we use :
		// checkField(field, fieldDescForJUnitMessage, name, list, mandatory, itemMandatory, typeName, clazz)
		//
		// withoutParameters: [Character]!
		checkField(type, j, "withoutParameters", true, true, false, "Character",
				pluginConfiguration.getPackageName() + ".Character");
		j += 1;
		// withOneOptionalParam(character: Character): Character
		checkField(type, j, "withOneOptionalParam", false, false, null, "Character",
				pluginConfiguration.getPackageName() + ".Character");
		checkInputParameter(type, j, 0, "character", false, false, null, "Character",
				pluginConfiguration.getPackageName() + ".Character", null);
		j += 1;
		// withOneMandatoryParam(character: Character!): Character
		checkField(type, j, "withOneMandatoryParam", false, false, false, "Character",
				pluginConfiguration.getPackageName() + ".Character");
		checkInputParameter(type, j, 0, "character", false, true, null, "Character",
				pluginConfiguration.getPackageName() + ".Character", null);
		j += 1;
		// withOneMandatoryParamDefaultValue(character: Character! = "no one"): Character!
		checkField(type, j, "withOneMandatoryParamDefaultValue", false, true, false, "Character",
				pluginConfiguration.getPackageName() + ".Character");
		checkInputParameter(type, j, 0, "character", false, true, null, "Character",
				pluginConfiguration.getPackageName() + ".Character", "no one");
		j += 1;
		// withTwoMandatoryParamDefaultVal(theHero: Droid! = "A droid", index: int = "Not a number, but ok !!"): Droid!
		checkField(type, j, "withTwoMandatoryParamDefaultVal", false, true, null, "Droid",
				pluginConfiguration.getPackageName() + ".Droid");
		checkInputParameter(type, j, 0, "theHero", false, true, null, "Droid",
				pluginConfiguration.getPackageName() + ".Droid", "A droid");
		checkInputParameter(type, j, 1, "index", false, false, null, "int", "java.lang.Integer",
				"Not a number, but ok !!");
		j += 1;
		// withEnum(episode: Episode!): Character
		checkField(type, j, "withEnum", false, false, null, "Character",
				pluginConfiguration.getPackageName() + ".Character");
		checkInputParameter(type, j, 0, "episode", false, true, null, "Episode",
				pluginConfiguration.getPackageName() + ".Episode", null);
		j += 1;
		// withList(name: String!, friends: [Character]!): [Characters]
		checkField(type, j, "withList", true, false, false, "Character",
				pluginConfiguration.getPackageName() + ".Character");
		checkInputParameter(type, j, 0, "name", false, true, null, "String", String.class.getName(), null);
		checkInputParameter(type, j, 1, "friends", true, true, false, "Character",
				pluginConfiguration.getPackageName() + ".Character", null);
		j += 1;
	}

	@Test
	@DirtiesContext
	void test_readEnumType() {
		// Preparation
		String objectName = "Episode";
		EnumTypeDefinition def = null;
		for (Definition<?> node : documents.get(0).getDefinitions()) {
			if (node instanceof EnumTypeDefinition && ((EnumTypeDefinition) node).getName().equals(objectName)) {
				def = (EnumTypeDefinition) node;
			}
		} // for
		assertNotNull(def, "We should have found our test case (" + objectName + ")");
		// To be sure to properly find our parsed object type, we empty the documentParser objects list.
		documentParser.queryTypes = new ArrayList<>();

		// Go, go, go
		EnumType type = documentParser.readEnumType(def);

		// Verification
		assertEquals(objectName, type.getName(), "The name is " + objectName);
		assertEquals(3, type.getValues().size(), "Number of values");

		int i = 0;
		assertEquals("NEWHOPE", type.getValues().get(i++));
		assertEquals("EMPIRE", type.getValues().get(i++));
		assertEquals("JEDI", type.getValues().get(i++));
	}

	@Test
	@DirtiesContext
	void test_addObjectType_MutationType() {
		// Preparation
		String objectName = "AnotherMutationType";
		ObjectTypeDefinition def = null;
		for (Definition<?> node : documents.get(0).getDefinitions()) {
			if (node instanceof ObjectTypeDefinition && ((ObjectTypeDefinition) node).getName().equals(objectName)) {
				def = (ObjectTypeDefinition) node;
			}
		} // for
		assertNotNull(def, "We should have found our test case (" + objectName + ")");
		// We need to parse the whole document, to get the types map filled.
		documentParser.parseDocuments();
		// To be sure to properly find our parsed object type, we empty the documentParser objects list.
		documentParser.mutationTypes = new ArrayList<>();

		// Go, go, go
		ObjectType type = documentParser.readObjectType(def);

		// Verification
		assertEquals(objectName, type.getName());
		assertEquals(1, type.getFields().size());

		int j = 0;
		// Each mutation is actually a field. So we use :
		// checkField(field, fieldDescForJUnitMessage, name, list, mandatory, itemMandatory, typeName, clazz)
		// checkInputParameter(type, j, numParam, name, list, mandatory, itemMandatory, typeName, classname,
		// defaultValue)
		//
		// createHuman(human: Human!): Human!
		checkField(type, j, "createHuman", false, true, null, "Human", pluginConfiguration.getPackageName() + ".Human");
		checkInputParameter(type, j, 0, "human", false, true, null, "Human",
				pluginConfiguration.getPackageName() + ".Human", null);
		j += 1;
	}

	@Test
	@DirtiesContext
	void test_addObjectType_SubscriptionType() {
		// Preparation
		String objectName = "TheSubscriptionType";
		ObjectTypeDefinition def = null;
		for (Definition<?> node : documents.get(0).getDefinitions()) {
			if (node instanceof ObjectTypeDefinition && ((ObjectTypeDefinition) node).getName().equals(objectName)) {
				def = (ObjectTypeDefinition) node;
			}
		} // for
		assertNotNull(def, "We should have found our test case (" + objectName + ")");
		// We need to parse the whole document, to get the types map filled.
		documentParser.parseDocuments();
		// To be sure to properly find our parsed object type, we empty the documentParser objects list.
		documentParser.subscriptionTypes = new ArrayList<>();

		// Go, go, go
		ObjectType type = documentParser.readObjectType(def);

		// Verification
		assertEquals(objectName, type.getName());
		assertEquals(1, type.getFields().size());

		int j = 0;
		// Each mutation is actually a field. So we use :
		// checkField(field, fieldDescForJUnitMessage, name, list, mandatory, itemMandatory, typeName, clazz)
		// checkInputParameter(type, j, numParam, name, list, mandatory, itemMandatory, typeName, classname,
		// defaultValue)
		//
		// subscribeNewHumanForEpisode(episode: Episode! = NEWHOPE): Human!
		checkField(type, j, "subscribeNewHumanForEpisode", false, true, null, "Human",
				pluginConfiguration.getPackageName() + ".Human");
		checkInputParameter(type, j, 0, "episode", false, true, null, "Episode",
				pluginConfiguration.getPackageName() + ".Episode", "NEWHOPE");
		j += 1;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void checkField(ObjectType type, int j, String name, boolean list, boolean mandatory, Boolean itemMandatory,
			String typeName, String classname) {
		Field field = type.getFields().get(j);
		String fieldDescForJUnitMessage = "Field n°" + j + " (" + name + ")";

		assertEquals(name, field.getName(), "field name is " + name + " (for " + fieldDescForJUnitMessage + ")");
		assertEquals(list, field.isList(), "field list is " + list + " (for " + fieldDescForJUnitMessage + ")");
		assertEquals(mandatory, field.isMandatory(),
				"field mandatory is " + mandatory + " (for " + fieldDescForJUnitMessage + ")");
		if (itemMandatory != null) {
			assertEquals(itemMandatory, field.isItemMandatory(),
					"field itemMandatory is " + itemMandatory + " (for " + fieldDescForJUnitMessage + ")");
		}

		Type fieldType = field.getType();
		assertEquals(typeName, fieldType.getName(),
				"type name is " + typeName + " (for " + fieldDescForJUnitMessage + ")");
		assertEquals(classname, fieldType.getClassFullName(),
				"Class for field type is " + classname + " (for " + fieldDescForJUnitMessage + ")");
	}

	private void checkInputParameter(ObjectType type, int j, int numParam, String name, boolean list, boolean mandatory,
			Boolean itemMandatory, String typeName, String classname, String defaultValue) {
		Field inputValue = type.getFields().get(j).getInputParameters().get(numParam);

		String intputParamDescForJUnitMessage = "Field n°" + j + " / input param n°" + numParam;

		assertEquals(name, inputValue.getName(), "name is " + name + " (for " + intputParamDescForJUnitMessage + ")");
		assertEquals(list, inputValue.isList(), "list is " + list + " (for " + intputParamDescForJUnitMessage + ")");
		assertEquals(mandatory, inputValue.isMandatory(),
				"mandatory is " + mandatory + " (for " + intputParamDescForJUnitMessage + ")");
		if (itemMandatory != null) {
			assertEquals(itemMandatory, inputValue.isItemMandatory(),
					"itemMandatory is " + itemMandatory + " (for " + intputParamDescForJUnitMessage + ")");
		}

		Type fieldType = inputValue.getType();
		assertEquals(typeName, fieldType.getName(),
				"name is " + typeName + " (for " + intputParamDescForJUnitMessage + ")");
		assertEquals(classname, fieldType.getClassFullName(),
				"Class type is " + classname + " (for " + intputParamDescForJUnitMessage + ")");

		assertEquals(defaultValue, inputValue.getDefaultValue(),
				"Default Value is <" + defaultValue + "> (for " + intputParamDescForJUnitMessage + ")");
	}
}
