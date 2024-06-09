package com.dropchat.cinemaxmovie.configuration;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.dropchat.cinemaxmovie.converter.response.ApiResponse;
import com.dropchat.cinemaxmovie.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        response.setStatus(errorCode.getStatusCode().value()); // Sets the status code for this response
        response.setContentType(
                MediaType.APPLICATION_JSON_VALUE); // Sets the content type of the response being sent to the client, if
        // the response has not been committed yet
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));

        response.flushBuffer(); // Forces any content in the buffer to be written to the client. A call to this method
        // automatically commits the response, meaning the status code and headers will be
        // written
    }
}
