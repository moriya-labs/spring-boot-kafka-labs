package me.moriya.labs.producer.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.moriya.labs.producer.api.ProducerResource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig
{

    @Bean
    public Docket producerApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Producer API")
                .apiInfo(info())
                .select()
                .apis(RequestHandlerSelectors.basePackage(String.format("%s.%s", ProducerResource.class.getPackageName(), "api")))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    private ApiInfo info()
    {
        return new ApiInfoBuilder()
                .title("Producer API")
                .version("1.0.0")
                .contact(new Contact("André Moriya", "https://www.linkedin.com/in/andremoriya/", "andremoriya@gmail.com"))
                .build();
    }

}
