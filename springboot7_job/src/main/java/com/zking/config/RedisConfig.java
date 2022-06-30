package com.zking.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

//标注为配置类
@Configuration
//开启rides缓存注解
@EnableCaching
public class RedisConfig {
    @Bean
    public CacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheConfiguration config = RedisCacheConfiguration
                //默认配置对象
                .defaultCacheConfig()
                //key的序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(redisTemplate.getStringSerializer()))
                //value的序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(redisTemplate.getValueSerializer()))
                //禁止缓存空值
                .disableCachingNullValues()
                //存活时间
                .entryTtl(Duration.ofHours(1));//一小时
                //.entryTtl(Duration.ofSeconds());//10秒

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisTemplate.getConnectionFactory())
                .cacheDefaults(config)// 配置
                .transactionAware()// 事务感知
                .build();

    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //配置key-value的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //配置hash表的序列化器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //配置生效
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
