package com.codeartist.component.autoconfigure.captcha;

import com.codeartist.component.cache.support.RedisCache;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * 验证码校验配置
 *
 * @author AiJiangnan
 * @date 2022/9/23
 */
@SpringBootConfiguration
@ConditionalOnBean(RedisCache.class)
public class CaptchaAutoConfiguration {

}
