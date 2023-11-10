package com.codeartist.component.autoconfigure.cache;

import com.codeartist.component.cache.aop.LocalCacheAnnotationAspect;
import com.codeartist.component.cache.aop.RedisCacheAnnotationAspect;
import com.codeartist.component.cache.bean.CacheProperties;
import com.codeartist.component.cache.metric.CacheMetrics;
import com.codeartist.component.cache.support.RedisCacheTemplate;
import com.codeartist.component.cache.support.caffeine.LocalCacheTemplate;
import com.codeartist.component.cache.trace.CacheTraces;
import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.core.support.trace.Tracers;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * 缓存组件
 *
 * @author 艾江南
 * @date 2019/4/19
 */
@SpringBootConfiguration
@ConditionalOnClass(CacheProperties.class)
@EnableConfigurationProperties(CacheProperties.class)
@Import({RedisAutoConfiguration.class, RedisMultiAutoConfiguration.class})
public class CacheAutoConfiguration {

    /**
     * Redis缓存注解切面
     */
    @Bean
    public RedisCacheAnnotationAspect cacheAnnotationAspect(Map<String, RedisCacheTemplate> redisCacheTemplateMap) {
        return new RedisCacheAnnotationAspect(redisCacheTemplateMap);
    }

    /**
     * 本地缓存注解切面
     */
    @Bean
    public LocalCacheAnnotationAspect localCacheAnnotationAspect(LocalCacheTemplate localCacheTemplate) {
        return new LocalCacheAnnotationAspect(localCacheTemplate);
    }

    @Bean
    public CacheMetrics cacheMetrics(Metrics metrics) {
        return new CacheMetrics(metrics);
    }

    @Bean
    public CacheTraces cacheTraces(Tracers tracers) {
        return new CacheTraces(tracers);
    }

    @Bean
    public LocalCacheTemplate localCacheTemplate() {
        return new LocalCacheTemplate();
    }
}
