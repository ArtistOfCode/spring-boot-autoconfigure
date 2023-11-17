package com.codeartist.component.autoconfigure.sleuth;

import brave.Tracer;
import brave.Tracing;
import brave.sampler.Sampler;
import com.codeartist.component.core.support.trace.NoopTracers;
import com.codeartist.component.core.support.trace.Tracers;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.sleuth.autoconfig.brave.SamplerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 链路追踪组件
 *
 * @author AiJiangnan
 * @date 2021/9/7
 */
@SpringBootConfiguration
@ConditionalOnProperty(value = {"spring.sleuth.enabled"}, matchIfMissing = true)
@Import(TraceAutoConfiguration.SleuthTraceAutoConfiguration.class)
public class TraceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(Tracers.class)
    public Tracers defaultTracers() {
        return new NoopTracers();
    }

    @SpringBootConfiguration
    @ConditionalOnClass(Tracer.class)
    static class SleuthTraceAutoConfiguration {

        @Bean
        @ConditionalOnBean(Tracer.class)
        public SleuthTracers sleuthTracers(Tracer tracer, Tracing tracing) {
            return new SleuthTracers(tracer, tracing);
        }


        /**
         * 默认全部取样，后续优化
         */
        @Bean
        public Sampler defaultTraceSampler(SamplerProperties config) {
            // TODO 升级框架
//        if (config.getProbability() != null) {
//            return new ProbabilityBasedSampler(config);
//        }
            return Sampler.ALWAYS_SAMPLE;
        }
    }
}
