package com.codeartist.component.autoconfigure.metrics;

import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.metric.core.MetricRegistry;
import com.codeartist.component.metric.core.PrometheusMetricsBinder;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.metrics.web.servlet.DefaultWebMvcTagsProvider;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Micrometer监控自动注入
 *
 * @author AiJiangnan
 * @date 2021/5/22
 */
@SpringBootConfiguration
@ConditionalOnClass({MeterRegistry.class, MetricRegistry.class, PrometheusMetricsBinder.class})
public class MetricsRegistryAutoConfiguration {

    @Bean
    public Metrics metrics(MeterRegistry meterRegistry) {
        return new MetricRegistry(meterRegistry);
    }

    @Bean
    public PrometheusMetricsBinder prometheusMetricsBinder(Metrics metrics) {
        return new PrometheusMetricsBinder(metrics);
    }

    @Bean
    public WebMvcTagsProvider webMvcTagsProvider() {
        return new DefaultWebMvcTagsProvider() {
            @Override
            public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
                return Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, response), WebMvcTags.status(response));
            }
        };
    }

}

