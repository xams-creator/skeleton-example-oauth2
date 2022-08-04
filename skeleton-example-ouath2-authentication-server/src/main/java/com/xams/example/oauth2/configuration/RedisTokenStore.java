package com.xams.example.oauth2.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class RedisTokenStore extends org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore {

    @Value("${spring.redis.key-prefix:}")
    private String prefix;

    RedisTokenStore(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @PostConstruct
    void init() {
        setPrefix(prefix);
    }

}
