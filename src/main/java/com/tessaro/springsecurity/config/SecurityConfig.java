package com.tessaro.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        This is DEFAULT
//        http
//                .authorizeRequests()
//                    .anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                .and()
//                .httpBasic();
//        END OF DEFAULT

        http.authorizeRequests()
                .antMatchers("/notices").permitAll()
                .antMatchers("/contact").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();

//        http.exceptionHandling().accessDeniedPage("/login");
    };

    // FORMA EM MEMORIA (NÃO É CORRETO USAR ASSIM EM AMBIENTE DE PRODUÇÃO)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication().withUser("admin").password("admin").authorities("admin")
      .and().withUser("user").password("user").authorities("read")
              .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
    };

    // FORMA CORRETA
//    @Autowired
//    private AutenticacaoService autenticacaoService;
    //
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
//    }
}
