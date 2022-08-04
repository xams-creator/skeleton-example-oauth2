package com.xams.example.oauth2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    class StringObjectRedisTemplate extends RedisTemplate<String, Object> {

        StringObjectRedisTemplate() {
            setKeySerializer(RedisSerializer.string());
        }

        StringObjectRedisTemplate(RedisConnectionFactory connectionFactory) {
            this();
            setConnectionFactory(connectionFactory);
            afterPropertiesSet();
        }

    }

    @Bean
    public RedisTemplate<String, Object> jsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringObjectRedisTemplate(connectionFactory);
    }

    @Bean
    public StringRedisSerializer prefixStringRedisSerializer() {
        return new PrefixStringRedisSerializer();
    }
}
