package com.codeartist.component.autoconfigure.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.codeartist.component.core.support.curd.RelationService;
import com.codeartist.component.core.support.curd.RelationServiceImpl;
import com.codeartist.component.core.support.serializer.JacksonSerializer;
import com.codeartist.component.core.support.uuid.DefaultIdGenerator;
import com.codeartist.component.core.support.uuid.IdGenerator;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;

/**
 * MyBatis自动配置
 *
 * @author AiJiangnan
 * @date 2022/9/2
 */
@SpringBootConfiguration
@ConditionalOnClass({MybatisPlusInterceptor.class, JacksonTypeHandler.class})
@Import(MyBatisConfiguration.TransactionAutoConfiguration.class)
public class MyBatisConfiguration {

    @PostConstruct
    public void init() {
        // MyBatisPlus类型处理器默认的JSON序列化配置
        JacksonTypeHandler.setObjectMapper(JacksonSerializer.simpleMapper());
    }

    /**
     * MyBatisPlus拦截器配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 分布式ID生成器，默认实现，后续可以考虑单独封装ID生成器
     * <p>
     * TODO 重新优化
     */
    @Bean
    @ConditionalOnMissingBean
    public IdGenerator idGenerator() {
        return new DefaultIdGenerator();
    }

    @Bean
    public RelationService relationService() {
        return new RelationServiceImpl();
    }


    /**
     * 事务配置
     *
     * @author AiJiangnan
     * @date 2022/7/22
     */
    @SpringBootConfiguration
    @ConditionalOnClass(PlatformTransactionManager.class)
    @ConditionalOnBean(PlatformTransactionManager.class)
    public static class TransactionAutoConfiguration {

        /**
         * 全局编程式事务配置
         */
        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager txManager) {
            TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
            transactionTemplate.setTimeout(30);
            return transactionTemplate;
        }
    }
}
