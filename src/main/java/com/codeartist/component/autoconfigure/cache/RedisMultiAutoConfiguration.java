package com.codeartist.component.autoconfigure.cache;

import com.codeartist.component.cache.multi.RedisMultiConnectionFactory;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Redis多数据源自动配置
 *
 * @author 艾江南
 * @date 2022/12/1
 */
@SpringBootConfiguration
public class RedisMultiAutoConfiguration {

    @Bean(destroyMethod = "shutdown")
    DefaultClientResources lettuceClientResources() {
        return DefaultClientResources.create();
    }

    @Bean
    public RedisMultiConnectionFactory redisMultiConnectionFactory() {
        return new RedisMultiConnectionFactory();
    }
}
