package com.dropchat.cinemaxmovie.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomJWTDecocder jwtDecocder;
    private final String[] PUBLIC_ENDPOINTS = {
            "/users/signup",
            "/users/login",
            "/auth/introspect",
            "/users/my-info",
            "/users/logout",
            "/users/refresh-token"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(request ->
                        request.requestMatchers(PUBLIC_ENDPOINTS).permitAll() //Specify that URLs are allowed by anyone
//                        .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ROLE_ADMIN")
                                .anyRequest() //Maps any request.
                                .authenticated() //Specify that URLs are allowed by any authenticated user.
        );

        httpSecurity.oauth2ResourceServer(oauth2 ->
                //Enables Jwt-encoded bearer token support.
                oauth2.jwt(jwtConfigurer ->
                                jwtConfigurer.decoder(jwtDecocder)
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                        .authenticationEntryPoint(new JWTAuthenticationEntryPoint())
        );

        httpSecurity.csrf(x -> x.disable()); //Turn off CSRF

        return httpSecurity.build();
    }

    @Bean
    protected JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }



}
