package com.codeartist.component.autoconfigure.web;

import com.codeartist.component.core.annotation.AppName;
import com.codeartist.component.core.annotation.Development;
import com.codeartist.component.core.annotation.RootPackage;
import com.codeartist.component.core.entity.enums.Environments;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringBootConfiguration;
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
public class SwaggerAutoConfiguration {

    @Bean
    public OpenAPI openAPI(@AppName String name) {
        OpenAPI openAPI = new OpenAPI().info(new Info().title(name).version("v1"));
        if (Environments.LOCAL.not()) {
            openAPI.extensions(Collections.singletonMap("basePath", "/" + name));
        }
        return openAPI;
    }

    @Bean
    public GroupedOpenApi groupedOpenApi(@AppName String name, @RootPackage String rootPackage) {
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch("/api/**")
                .packagesToScan(rootPackage + ".controller")
                .build();
    }
}
