package com.ikolay.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class CompanySecurityServiceConfig {

        private static final String[] WHITELIST = {"/swagger-ui/**", "/v3/api-docs/**", "/api/v1/user/**","/api/v1/auth/**","/api/v1/company/**","/api/v1/transaction/**"};

        @Bean
        JwtTokenFilter getJwtTokenFilter() {
            return new JwtTokenFilter();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.csrf().disable();
//            httpSecurity.authorizeRequests().antMatchers(WHITELIST).permitAll().anyRequest().authenticated();
            httpSecurity.authorizeRequests().anyRequest().permitAll();
//            httpSecurity.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
        }
    }

