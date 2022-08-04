package com.xams.example.oauth2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private RedisTokenStore tokenStore;

    @Value("${spring.application.name:}")
    private String appName;

//    @Autowired
//    private DataSource dataSource;

//    @Bean
//    public TokenStore jdbcTokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(appName)//指定当前资源的id，非常重要！必须写！
            .tokenStore(tokenStore);//指定保存token的方式
    }

    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            //指定不同请求方式访问资源所需要的权限，一般查询是read，其余是write。
            .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
            .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
            .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
            .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
            .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
            .and()
            .headers().addHeaderWriter((request, response) -> {
                response.addHeader("Access-Control-Allow-Origin", "*");//允许跨域
                if (request.getMethod().equals("OPTIONS")) {//如果是跨域的预检请求，则原封不动向下传达请求头信息
                    response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
                }
            });
    }
}
