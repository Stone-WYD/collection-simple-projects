package com.wyd.zmhkmiddleware.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;


@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "dockerBean")
    public Docket dockerBean(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        // 描述字段
                        .title("zm-hk")
                        .description("中免海康对接平台")
                        // 团队地址 .termsOfServiceUrl("")
                        // 联系我们 .contact("")
                        .version("1.0")
                        .build()
                )
                // 分组名称
                // .groupName("springboot-1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wyd.zmhkmiddleware.business.web"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }

}
