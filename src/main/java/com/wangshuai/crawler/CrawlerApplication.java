package com.wangshuai.crawler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import xin.allonsy.utils.mybatis.MybatisUtils;

/**
 * @author wangshuai
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@ComponentScan(basePackages = {"com.wangshuai", "xin.allonsy"})
@MapperScan("com.wangshuai.crawler.dal.mapper")
public class CrawlerApplication {

    static {
        MybatisUtils.logCompleteSql("com.wangshuai.crawler.dal.mapper");
    }

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }
}
