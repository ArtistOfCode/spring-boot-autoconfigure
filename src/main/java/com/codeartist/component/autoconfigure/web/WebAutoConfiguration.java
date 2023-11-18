package com.codeartist.component.autoconfigure.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.support.auth.AuthContext;
import com.codeartist.component.core.support.auth.DefaultAuthContext;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * Web全局配置
 *
 * @author AiJiangnan
 * @date 2022/7/15
 */
@SpringBootConfiguration
@Import({SwaggerAutoConfiguration.class, WebMvcAutoConfiguration.class, WebReactiveAutoConfiguration.class})
public class WebAutoConfiguration {

    @Bean
    public SpringContext springContext() {
        return new SpringContext();
    }

    @Bean
    @ConditionalOnMissingBean(AuthContext.class)
    public AuthContext authContext() {
        return new DefaultAuthContext();
    }
}
