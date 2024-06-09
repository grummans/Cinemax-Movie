package com.dropchat.cinemaxmovie.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dropchat.cinemaxmovie.converter.request.IntrospectRequest;
import com.dropchat.cinemaxmovie.converter.response.ApiResponse;
import com.dropchat.cinemaxmovie.converter.response.IntrospectResponse;
import com.dropchat.cinemaxmovie.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspectTokenUser(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.validateTokenUser(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }
}
