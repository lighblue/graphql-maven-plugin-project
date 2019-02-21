package graphql.mavenplugin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.maven.plugin.logging.Log;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import graphql.mavenplugin.language.ObjectType;
import graphql.mavenplugin.test.helper.AllGraphQLCasesSpringConfiguration;
import graphql.mavenplugin.test.helper.MavenTestHelper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AllGraphQLCasesSpringConfiguration.class })
class CodeGeneratorTest {

	@Resource
	String basePackage;
	@Resource
	Log log;
	@Resource
	MavenTestHelper mavenTestHelper;

	private File targetSourceFolder;
	private CodeGenerator codeGenerator;

	@BeforeEach
	void setUp() throws Exception {
		targetSourceFolder = mavenTestHelper.getTargetSourceFolder(this.getClass().getSimpleName());

		codeGenerator = new CodeGenerator();
		codeGenerator.basePackage = basePackage;
		codeGenerator.log = log;
		codeGenerator.targetSourceFolder = targetSourceFolder;
	}

	@Test
	void testCodeGenerator() {
		assertNotNull(codeGenerator.velocityEngine, "Velocity engine must be initialized");
	}

	/**
	 * This test is important, as it checks the Velocity context which is sent to the templates. As it will be possible
	 * for users of the plugin, to define their own templates, the Velocity context is a kind of API, exposed to other's
	 * templates. It must remain stable. Field can be added. <B>But no field should be removed</B>.
	 */
	@Test
	void test_generateTargetFile() {
		// Let's mock the Velocity engine, to check how it is called
		codeGenerator.velocityEngine = mock(VelocityEngine.class);
		Template mockedTemplate = mock(Template.class);
		when(codeGenerator.velocityEngine.getTemplate(anyString())).thenReturn(mockedTemplate);

		ObjectType object1 = new ObjectType();
		ObjectType object2 = new ObjectType();
		List<ObjectType> objects = new ArrayList<>();
		objects.add(object1);
		objects.add(object2);

		String type = "my test type";
		String templateFilename = "folder/a_template_for_test.vm";

		// Go, go, go
		int i = codeGenerator.generateTargetFile(objects, type, templateFilename);

		// Verification
		assertEquals(objects.size(), i, "Nb files generated");

		// Let's check the parameter for getTemplate
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		verify(codeGenerator.velocityEngine, times(2)).getTemplate(argument.capture());
		assertEquals(templateFilename, argument.getValue(), "checks the parameter for getTemplate");

		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Let's check the velocity context sent to the template ... THIS IS IMPORTANT! DO NOT BREAK IT!
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		ArgumentCaptor<Context> argumentContext = ArgumentCaptor.forClass(Context.class);
		verify(mockedTemplate, times(2)).merge(argumentContext.capture(), any(Writer.class));
		// We have the Context sent to the Template.merge(..) method. Let's check its content
		assertEquals(basePackage, argumentContext.getValue().get("package"), "Context: checks the package");
		assertEquals(object1, argumentContext.getValue().get("object"), "Context: checks the package");
		assertEquals(type, argumentContext.getValue().get("type"), "Context: checks the package");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
	}

	@Test
	void testGetJavaFile() throws IOException {
		// Preparation
		String name = "MyClass";
		codeGenerator.basePackage = basePackage;
		codeGenerator.targetSourceFolder = targetSourceFolder;

		// Go, go, go
		File file = codeGenerator.getJavaFile(name);

		// Verification
		String expectedEndOfPath = (targetSourceFolder.getCanonicalPath() + '/' + basePackage + '/' + name)
				.replace('.', '/').replace('\\', '/') + ".java";
		assertEquals(expectedEndOfPath, file.getCanonicalPath().replace('\\', '/'), "The file path should end with "
				+ expectedEndOfPath + ", but is " + file.getCanonicalPath().replace('\\', '/'));
	}

}