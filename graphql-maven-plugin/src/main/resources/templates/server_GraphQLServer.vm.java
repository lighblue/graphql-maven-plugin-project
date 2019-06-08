package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author generated by graphql-java-generator
 * @See https://github.com/graphql-java-generator/graphql-java-generator
 */
@SpringBootApplication
@EnableConfigurationProperties
public class GraphQLServer#if($packaging=="war") extends SpringBootServletInitializer#end {

#if($packaging=="war")
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GraphQLServer.class);
	}
#end

	public static void main(String[] args) {
		SpringApplication.run(GraphQLServer.class, args);
	}

}
