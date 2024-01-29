package com.codeartist.component.autoconfigure.swagger;

import com.codeartist.component.core.annotation.Development;
import com.codeartist.component.core.entity.enums.Constants;
import com.codeartist.component.core.entity.enums.Environments;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import java.util.Collections;

/**
 * Swagger自动配置（Web MVC）
 *
 * @author AiJiangnan
 * @date 2022/8/18
 */
@Development
@SpringBootConfiguration
@ConditionalOnClass(org.springdoc.webmvc.ui.SwaggerConfig.class)
public class SwaggerAutoConfiguration {

    @Value(Constants.APPLICATION_NAME_KEY)
    private String appName;
    @Value(Constants.ROOT_PACKAGE_KEY)
    private String rootPackage;
    @Autowired
    private Environment environment;

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI().info(new Info().title(appName).version("v1"));
        if (!environment.acceptsProfiles(Profiles.of(Environments.LOCAL.getProfile()))) {
            openAPI.extensions(Collections.singletonMap("basePath", "/api/" + appName));
        }
        return openAPI;
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group(appName)
                .packagesToScan(rootPackage + ".controller")
                .build();
    }
}
