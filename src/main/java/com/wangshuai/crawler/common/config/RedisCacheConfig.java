package com.wangshuai.crawler.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.annotation.Resource;
import java.time.Duration;

@Configuration
@EnableCaching
@Slf4j
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${cacheManager.redis.defaultTTL}")
    private Long defaultTTL;

    @SuppressWarnings("unchecked")
    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheWriter redisCacheWriter =
            RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisCacheConfiguration redisCacheConfiguration =
            RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(defaultTTL));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName()).append("_");
            sb.append(method.getName()).append("_");
            for (Object obj : params) {
                sb.append(obj.toString());
                sb.append("_");
            }
            return sb.toString();
        };
    }
}
