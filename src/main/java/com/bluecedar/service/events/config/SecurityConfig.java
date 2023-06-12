package com.bluecedar.service.events.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnWebApplication
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers(toArray(List.of("logevents"))).permitAll();

        http
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling();
    }

    protected String[] toArray(List<String> list) {
        return list.toArray(new String[0]);
    }
}
