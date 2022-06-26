package com.zking.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //配置key-value的序列化器
        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //配置hash表的序列化器
        redisTemplate.setHashKeySerializer(redisTemplate.getStringSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //配置生效
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

//    @Bean
//    public RedisConnectionFactory factory() {
//        return new JedisConnectionFactory();
//    }
}
