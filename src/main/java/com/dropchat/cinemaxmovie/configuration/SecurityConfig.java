package com.dropchat.cinemaxmovie.configuration;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.MACSigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${application.jwt.token.Signerkey}")
    private String SIGNER_KEY;
    private final String[] PUBLIC_ENDPOINTS = {
            "/users/signup",
            "users/login",
            "/auth/introspect"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest() //Maps any request.
                        .authenticated() //Specify that URLs are allowed by any authenticated user.
        );

        httpSecurity.oauth2ResourceServer(oauth2 ->
                //Enables Jwt-encoded bearer token support.
                oauth2.jwt(jwtConfigurer ->
                        jwtConfigurer.decoder(decoder()))
        );

        httpSecurity.csrf(x -> x.disable()); //Turn off CSRF

        return httpSecurity.build();
    }

    @Bean
    protected JwtDecoder decoder(){

        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), JWSAlgorithm.HS512.toString());

        return NimbusJwtDecoder.withSecretKey(secretKeySpec) //Use the given SecretKey to validate the MAC on a JSON Web Signature (JWS).
                .macAlgorithm(MacAlgorithm.HS512) //Use the given algorithm  when generating the MAC
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}
