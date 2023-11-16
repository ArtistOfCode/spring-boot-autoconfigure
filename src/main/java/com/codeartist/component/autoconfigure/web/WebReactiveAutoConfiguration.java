package com.codeartist.component.autoconfigure.web;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;

/**
 * Web Reactive 配置
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
@SpringBootConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebReactiveAutoConfiguration {

}
