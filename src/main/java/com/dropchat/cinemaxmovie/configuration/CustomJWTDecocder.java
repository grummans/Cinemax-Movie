package com.dropchat.cinemaxmovie.configuration;

import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.dropchat.cinemaxmovie.converter.request.IntrospectRequest;
import com.dropchat.cinemaxmovie.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomJWTDecocder implements JwtDecoder {

    @Value("${application.jwt.token.Signerkey}")
    private String singerKey;

    private final AuthenticationService authenticationService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var response = authenticationService.validateTokenUser(
                    IntrospectRequest.builder().token(token).build());
            if (!response.isValid()) {
                throw new JwtException("Token invalid");
            }
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(singerKey.getBytes(), JWSAlgorithm.HS512.toString());

            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(
                            secretKeySpec) // Use the given SecretKey to validate the MAC on a JSON Web Signature (JWS).
                    .macAlgorithm(MacAlgorithm.HS512) // Use the given algorithm  when generating the MAC
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
