package com.tessaro.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

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
            "/notices",
            "/contact"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

        http.authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();

//        http.exceptionHandling().accessDeniedPage("/login");
    };

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//        UserDetails user = User.withUsername("admin").password("admin").authorities("admin").build();
//        UserDetails user2 = User.withUsername("user").password("user").authorities("read").build();
//        userDetailsService.createUser(user);
//        userDetailsService.createUser(user2);
//        auth.userDetailsService(userDetailsService);
//    };

    // FORMA CORRETA
//    @Autowired
//    private AutenticacaoService autenticacaoService;
    //
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
//    }

    //Ao escrever esse BEAN, eu faço com que o spring security entenda que o tipo PasswordEncoder default será o definido no metodo abaixo,
    //Sendo utilizado por baixo dos panos no lugar do ".and().passwordEncoder(NoOpPasswordEncoder.getInstance());" dentro do metodo configure( AuthenticationManagerBuilder auth)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }
}
