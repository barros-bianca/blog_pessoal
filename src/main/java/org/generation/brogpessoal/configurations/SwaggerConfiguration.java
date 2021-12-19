package org.generation.brogpessoal.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI springOpenAPI () {
		return new OpenAPI ()
				.info(new Info()
						.title("Projeto Blog Pessoal")
						.description("Projeto Blog Pessoal - Generation Brasil")
						.version("v0.0.1")
				.license(new License()
						.name("Generation Brasil")
						.url("https://brazil.generation.org/"))
				.contact (new Contact()
						.name("Conteúdo Generation")
						.url("https://github.com/biancabs2/blog_pessoal")
						.email("biancabarrosufrj@gmail.com")))
						.externalDocs(new ExternalDocumentation()
								.description("GitHub Project")
								.url("https://github.com/biancabs2/blog_pessoal"));
	}
}
