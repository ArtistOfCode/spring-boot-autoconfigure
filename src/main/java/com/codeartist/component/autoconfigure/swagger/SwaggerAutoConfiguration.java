package com.codeartist.component.autoconfigure.swagger;

import com.codeartist.component.core.entity.enums.Environments;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

/**
 * Swagger自动配置
 *
 * @author 艾江南
 * @date 2022/8/18
 */
@SpringBootConfiguration
public class SwaggerAutoConfiguration {

    @Value("${spring.application.name}")
    private String name;
    @Value("${spring.root.package}")
    private String rootPackage;

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI().info(new Info()
                .title(name)
                .description("Idatafun service - " + name)
                .version("1.0.0"));
        if (Environments.LOCAL.not()) {
            openAPI.extensions(Collections.singletonMap("basePath", "/" + name));
        }
        return openAPI;
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch("/api/**")
                .packagesToScan(rootPackage + ".controller")
                .build();
    }
}
