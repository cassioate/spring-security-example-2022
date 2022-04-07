package com.tessaro.springsecurity.security.filterJWTv2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tessaro.springsecurity.security.model.SecurityUserDetailsImpl;
import com.tessaro.springsecurity.security.filterJWTv2.credentials.CredenciaisLogin;
import com.tessaro.springsecurity.security.filterJWTv2.failure_handler.JWTAuthenticationFailureHandler;
import com.tessaro.springsecurity.security.filterJWTv2.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtProperties jwtProperties;

    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtProperties jwtProperties) {
        //Mudando a msg que aparece na hora que falhar a autenticação.
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Authentication attemptAuthentication (HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        try {

            CredenciaisLogin creds = new ObjectMapper()
                    .readValue(req.getInputStream(), CredenciaisLogin.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken (creds.getEmail(),
                    creds.getPassword(), new ArrayList<>());

            //Esse utilizará a função loadUserByUsername da minha implementação do UserDetailService
            //E guardará em auth.getPrincipal() um usuario do tipo SecurityCustomerUserDetails.
            Authentication auth = authenticationManager.authenticate(authToken);

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    // Após autenticação ser bem sucedida será gerado o token JWT via codigo abaixo.
    protected void successfulAuthentication (HttpServletRequest req,
                                             HttpServletResponse res,
                                             FilterChain chain,
                                             Authentication auth) throws IOException, ServletException {

        Key secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        String usernameCustomerUserDetails = ((SecurityUserDetailsImpl) auth.getPrincipal()).getUsername();

        String token = Jwts.builder()
                .setSubject(usernameCustomerUserDetails)
                .claim("authorities", auth.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        res.addHeader("Authorization", "Bearer " + token);
    }

//    @Override
    // Após autenticação ser bem sucedida será gerado o token JWT via codigo abaixo.
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        SecurityContextHolder.clearContext();
//        this.logger.trace("Failed to process authentication request", failed);
//        this.logger.trace("Cleared SecurityContextHolder");
//        this.logger.trace("Handling authentication failure");
//        this.rememberMeServices.loginFail(request, response);
//        this.failureHandler.onAuthenticationFailure(request, response, failed);
//    }
}