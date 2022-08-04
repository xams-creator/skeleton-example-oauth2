package com.xams.example.oauth2;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xams.example.oauth2.business.**.mapper")
public class OAuth2AuthenticationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2AuthenticationServerApplication.class, args);
    }

}
