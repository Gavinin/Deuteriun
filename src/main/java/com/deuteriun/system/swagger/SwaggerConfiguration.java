package com.deuteriun.system.swagger;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
@ConfigurationProperties("swagger")
@Data
public class SwaggerConfiguration {
    private Boolean enable;

    private String applicationName;

    private String applicationVersion;

    private String applicationDescription;


    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .pathMapping("/")

                .enable(enable)

                .apiInfo(apiInfo())

                .select()

                //apis() 控制哪些接口暴露给swagger，
                // RequestHandlerSelectors.any() 所有都暴露
                // RequestHandlerSelectors.basePackage("com.keep.*")  指定包位置
                // withMethodAnnotation(ApiOperation.class)标记有这个注解 ApiOperation
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))

                .paths(PathSelectors.any())


                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName)
                .description(applicationDescription)
                .contact(new Contact("Gavinin", "", ""))
                .version(applicationVersion)
                .build();
    }

}
