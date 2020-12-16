package com.wangshuai.crawler.common.config;

import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Configuration;

/**
 * ApolloConfig
 *
 * @author wangshuai
 * @version V1.0
 * @date 2020-08-30 13:46
 */
@Configuration
@EnableApolloConfig
public class ApolloConfig {

    @ApolloJsonValue("${test.value}")
    private static String testValue;

    public static String getTestValue() {
        return testValue;
    }

}
