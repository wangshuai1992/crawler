package com.wangshuai.crawler.common.config;

import com.wangshuai.crawler.filter.CrossOriginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * web filter
 *
 * @author wangshuai
 * @version V1.0
 * @date 2018-01-07 20:18
 */
@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean crossOriginFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CrossOriginFilter());
        registration.addUrlPatterns("/*");
        registration.setName("crossOriginFilter");
        registration.setOrder(1);
        return registration;
    }

}
