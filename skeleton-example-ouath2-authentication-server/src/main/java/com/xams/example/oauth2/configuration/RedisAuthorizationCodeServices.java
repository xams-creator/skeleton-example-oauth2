package com.xams.example.oauth2.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;


@Component
@Slf4j
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    @Autowired
    private RedisTemplate<String, Object> redis;

    private String getOAuth2AuthenticationCodeKey(String client, String user, String code) {
        List<String> keys = new LinkedList<>();
        keys.add("xams");
        keys.add("user-center");
        keys.add("oauth");
        keys.add("authentication-code");
        keys.add(client);
        keys.add(user);
        keys.add(code);
        return String.join(":", keys);
    }

    private String getOAuth2AuthenticationCodeKey(String code) {
        List<String> keys = new LinkedList<>();
        keys.add("xams");
        keys.add("user-center");
        keys.add("oauth");
        keys.add("authentication-code");
        keys.add(code);
        return String.join(":", keys);
    }

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        OAuth2Request request = authentication.getOAuth2Request();
        String client = request.getClientId();
        String user = authentication.getUserAuthentication().getName();
        redis.opsForValue().set(getOAuth2AuthenticationCodeKey(code), authentication, Duration.ofMinutes(1));
        log.info("stored client user code : [{}]  [{}]  [{}] ", client, user, code);
//        redis.set(getOAuth2AuthenticationCodeKey(code), authentication, Duration.ofMinutes(1).seconds as int);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        return (OAuth2Authentication) redis.opsForValue().get(getOAuth2AuthenticationCodeKey(code));
//        return (OAuth2Authentication) redis.get(getOAuth2AuthenticationCodeKey(code))
    }

}
