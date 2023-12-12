package com.codeartist.component.autoconfigure.metrics;

import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.core.support.metric.NoOpMetrics;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 指标组件
 *
 * @author AiJiangnan
 * @date 2022/7/15
 */
@SpringBootConfiguration
@Import(MetricsRegistryAutoConfiguration.class)
public class MetricAutoConfiguration {

    /**
     * 默认使用指标收集
     */
    @Bean
    @ConditionalOnMissingBean(Metrics.class)
    public Metrics defaultMetrics() {
        return new NoOpMetrics();
    }
}
