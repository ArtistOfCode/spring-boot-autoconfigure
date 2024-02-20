package com.codeartist.component.autoconfigure.cache;

import com.codeartist.component.cache.CaffeineAutoConfiguration;
import com.codeartist.component.cache.RedisAutoConfiguration;
import com.codeartist.component.cache.aop.CacheInterceptor;
import com.codeartist.component.cache.aop.CacheOperationSource;
import com.codeartist.component.cache.aop.CachePointcutAdvisor;
import com.codeartist.component.cache.bean.CacheProperties;
import com.codeartist.component.cache.core.Cache;
import com.codeartist.component.cache.core.LocalCache;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * 缓存组件
 *
 * @author AiJiangnan
 * @date 2019/4/19
 */
@SpringBootConfiguration
@ConditionalOnClass(CacheProperties.class)
@EnableConfigurationProperties(CacheProperties.class)
@Import({CaffeineAutoConfiguration.class, RedisAutoConfiguration.class})
@AutoConfigureAfter(org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class)
public class CacheAutoConfiguration {

    @Bean
    public CacheOperationSource cacheOperationSource() {
        return new CacheOperationSource();
    }

    @Bean
    public CacheInterceptor cacheInterceptor(CacheOperationSource cacheOperationSource,
                                             Map<String, LocalCache> localCacheMap,
                                             Map<String, Cache> cacheMap) {
        CacheInterceptor interceptor = new CacheInterceptor();
        interceptor.setCacheOperationSource(cacheOperationSource);
        interceptor.setLocalCacheMap(localCacheMap);
        interceptor.setCacheMap(cacheMap);
        return interceptor;
    }

    @Bean
    public CachePointcutAdvisor cachePointcutAdvisor(CacheOperationSource cacheOperationSource, CacheInterceptor cacheInterceptor) {
        CachePointcutAdvisor advisor = new CachePointcutAdvisor();
        advisor.setAdvice(cacheInterceptor);
        advisor.setCacheOperationSource(cacheOperationSource);
        return advisor;
    }
}
