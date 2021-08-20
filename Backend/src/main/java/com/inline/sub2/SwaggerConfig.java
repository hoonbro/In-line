package com.inline.sub2;
//http://localhost:8080/api/v1/swagger-ui.html

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String version = "V1";
    private String title = "INLINE API " + version;

    @Bean
    public Docket api() {
        List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message("OK").build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message("서버 문제 발생.").responseModel(new ModelRef("Error")).build());
        responseMessages.add(new ResponseMessageBuilder().code(404).message("페이지를 찾을 수 없습니다.").build());
        return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes()).produces(getProduceContentTypes())
                .apiInfo(apiInfo()).groupName(version).select()
                .apis(RequestHandlerSelectors.basePackage("com.inline.sub2.api.controller"))
                .paths(postPaths()).build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,responseMessages);
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/xml;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    private Predicate<String> postPaths() {
//		return PathSelectors.any();
//		return or(regex("/admin/.*"), regex("/user/.*"));
        return regex(".*");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Inline sub2 API")
                .description("Inline sub2 Swagger")
                .contact(new Contact("Inline Swagger", "https://lab.ssafy.com/s05-webmobile1-sub2/S05P12D207", ""))
                .version("1.0")
                .build();
    }

}