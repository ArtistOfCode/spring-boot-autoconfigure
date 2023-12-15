package com.codeartist.component.autoconfigure.web;

import com.codeartist.component.core.annotation.Development;
import com.codeartist.component.core.entity.enums.Environments;
import com.codeartist.component.core.support.props.GlobalProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

/**
 * Swagger自动配置
 *
 * @author AiJiangnan
 * @date 2022/8/18
 */
@Development
@SpringBootConfiguration
@ConditionalOnClass(OpenAPI.class)
@AutoConfigureAfter(WebAutoConfiguration.class)
public class SwaggerAutoConfiguration {

    @Autowired
    private GlobalProperties globalProperties;

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI().info(new Info().title(globalProperties.getAppName()).version("v1"));
        if (Environments.LOCAL.not()) {
            openAPI.extensions(Collections.singletonMap("basePath", "/" + globalProperties.getAppName()));
        }
        return openAPI;
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group(globalProperties.getAppName())
                .pathsToMatch("/api/**")
                .packagesToScan(globalProperties.getRootPackage() + ".controller")
                .build();
    }
}
