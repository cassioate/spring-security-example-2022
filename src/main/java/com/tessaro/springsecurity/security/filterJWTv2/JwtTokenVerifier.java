package com.tessaro.springsecurity.security.filterJWTv2;

import com.tessaro.springsecurity.security.filterJWTv2.properties.JwtProperties;
import com.tessaro.springsecurity.security.service.SecurityUserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private SecurityUserDetailsServiceImpl securityUserDetailsService;

    private JwtProperties jwtProperties;

    public JwtTokenVerifier(SecurityUserDetailsServiceImpl securityUserDetailsService, JwtProperties jwtProperties) {
        this.securityUserDetailsService = securityUserDetailsService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {

            String token = authorizationHeader.replace("Bearer ", "");

            try {
                Key secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
                //Desmontará o TOKEN JWT, utilizando o segredo e o token com o "Bearer" ja removido.
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token);

                // Buscará o username, authorities e expirationDate para realizar a validação do token
                Claims body = claimsJws.getBody();
                String username = body.getSubject();
                //body.get("authorities") faz referencia ao campo .claim("authorities", auth.getAuthorities()) na hora da criação do token JWT.
                List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
                Date expirationDate = body.getExpiration();
                Date now = new Date(System.currentTimeMillis());

                if (username != null && expirationDate != null && now.before(expirationDate)) {
                    Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map( authority ->
                            new SimpleGrantedAuthority(authority.get("authority")))
                            .collect(Collectors.toSet());

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            simpleGrantedAuthorities
                    );

                    // Enviará para o contexto de segurança o usuario autenticado com suas AUTORIZAÇÕES DE ROLES Definidas, permitindo que a pessoa utilize a aplicação.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException e) {
                throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
            }
        }

        filterChain.doFilter(request, response);
    }
}