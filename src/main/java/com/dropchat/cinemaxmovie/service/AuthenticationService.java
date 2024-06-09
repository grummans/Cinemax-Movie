package com.dropchat.cinemaxmovie.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dropchat.cinemaxmovie.converter.request.AuthenticationRequest;
import com.dropchat.cinemaxmovie.converter.request.IntrospectRequest;
import com.dropchat.cinemaxmovie.converter.request.RefreshTokenRequest;
import com.dropchat.cinemaxmovie.converter.response.AuthenticationResponse;
import com.dropchat.cinemaxmovie.converter.response.IntrospectResponse;
import com.dropchat.cinemaxmovie.converter.response.MessageResponse;
import com.dropchat.cinemaxmovie.converter.response.RefreshTokenResponse;
import com.dropchat.cinemaxmovie.exception.ApplicationException;
import com.dropchat.cinemaxmovie.exception.ErrorCode;
import com.dropchat.cinemaxmovie.repository.*;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ConfirmEmailRepository confirmEmailRepository;
    private final UserStatusRepository userStatusRepository;
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @Value("${application.jwt.token.Signerkey}")
    private String SIGNER_KEY;

    public AuthenticationResponse authenticatedLoginUser(@NotNull AuthenticationRequest request) {

        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        boolean authenticated = encoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new ApplicationException(ErrorCode.USER_INVALID);
        }

        var token = generateToken(request.getUsername());
        var refreshToken = refreshTokenService.createRefreshToken(request.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken.getToken())
                .authenticate(true)
                .build();
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        var currentRefreshToken = refreshTokenService
                .findByToken(request.getToken())
                .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND));

        var accessToken = generateToken(currentRefreshToken.getUser().getUsername());
        var refreshToken = UUID.randomUUID().toString();
        currentRefreshToken.setToken(refreshToken);
        currentRefreshToken.setExpiredTime(
                new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()));

        refreshTokenRepository.save(currentRefreshToken);

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public IntrospectResponse validateTokenUser(IntrospectRequest request) throws JOSEException, ParseException {

        var token = request.getToken(); // get Token of user request
        boolean isValid = true;

        try {
            verifyToken(token);
        } catch (ApplicationException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid) // Return the result
                .build();
    }

    /**
     * Method to generate a token when client login success
     *
     * @param username get client username
     * @return a String token
     */
    private String generateToken(String username) {

        // Create a JWT Object with header & payload

        JWSHeader header =
                new JWSHeader(JWSAlgorithm.HS512); // JSON Web Signature (JWS) header. This class is immutable.

        JWTClaimsSet claimNames =
                new JWTClaimsSet.Builder() // JSON Web Token (JWT) claims set. This class is immutable.
                        .jwtID(UUID.randomUUID().toString())
                        .subject(username) // Sets the subject (sub) claim.
                        .issuer("com.dropchat.JWT") // Sets the issuer (iss) claim.
                        .issueTime(new Date()) // Sets the issued-at (iat) claim.
                        .expirationTime(new Date(Instant.now() // Sets the expiration time (exp) claim.
                                .plus(1, ChronoUnit.HOURS)
                                .toEpochMilli()))
                        .claim(
                                "scope",
                                userRepository
                                        .findByUsername(username)
                                        .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND))
                                        .getRole()
                                        .getRoleName())
                        .build();

        Payload payload =
                new Payload(claimNames.toJSONObject()); // Creates a new payload from the specified JSON object
        JWSObject jwsObject =
                new JWSObject(header, payload); // JSON Web Signature (JWS) secured object with compact serialisation
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes())); // Signs this JWS object with the specified signer
            return jwsObject
                    .serialize(); // Serialises this JWS object to its compact format consisting of Base64URL-encoded
            // parts delimited by period ('.') characters.
        } catch (JOSEException e) {
            throw new ApplicationException(ErrorCode.UNCATEGORIZED_ERROR);
        }
    }

    public MessageResponse verifyEmail(String otp) {

        // Find email by OTP Code
        var currentEmail = confirmEmailRepository
                .findByConfirmCode(otp)
                .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND));
        // Check if email is verified or not
        if (currentEmail.isConfirm()) {
            return new MessageResponse("Email is already verified");
        } else if (currentEmail.getExpiredTime().compareTo(new Date()) > 0) {
            currentEmail.setConfirm(true);
            currentEmail.getUser().setActive(true);
            currentEmail
                    .getUser()
                    .setUserStatus(userStatusRepository
                            .findNameByCode("True")
                            .orElseThrow(() -> new ApplicationException(ErrorCode.DATA_NOT_FOUND)));
            confirmEmailRepository.save(currentEmail);
            return new MessageResponse("Email verified");
        } else {
            currentEmail.setConfirmCode(currentEmail.getUser().getEmail());
            confirmEmailRepository.save(currentEmail);
            return new MessageResponse("Expired Time! Please regenerate otp and try again");
        }
    }

    protected SignedJWT verifyToken(String token) throws JOSEException, ParseException {

        JWSVerifier verifier =
                new MACVerifier(SIGNER_KEY.getBytes()); // Creates a new Message Authentication (MAC) verifier.

        SignedJWT signedJWT = SignedJWT.parse(
                token); // Parses a signed JSON Web Token (JWT) from the specified string in compact format

        Date exprateTime = signedJWT.getJWTClaimsSet().getExpirationTime(); // Gets the expiration time (exp) claim.

        var verifiedToken = signedJWT.verify(
                verifier); // (Boolean) Checks the signature of this JWS object with the specified verifier.

        if (!(verifiedToken && exprateTime.after(new Date())))
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }
}
