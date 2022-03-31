package com.tessaro.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    private static final String [] PUBLIC_MATCHERS = {
            "/h2-console/**"
    };

    private static final String [] PUBLIC_MATCHERS_GET = {
            "/notices"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.csrf().disable();
            http.headers().frameOptions().disable();
        } else {
            http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        }

        http.cors().configurationSource(request -> {
             CorsConfiguration config = new CorsConfiguration();
             config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
             config.setAllowedMethods(Collections.singletonList("*"));
             config.setAllowCredentials(true);
             config.setAllowedHeaders(Collections.singletonList("*"));
             config.setMaxAge(3600L);
             return config;
        }).and()
//                .csrf().disable()
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();

//        http.exceptionHandling().accessDeniedPage("/login");
    }



    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
