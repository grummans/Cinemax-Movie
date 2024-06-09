package com.dropchat.cinemaxmovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Cinemax Movie API", version = "1.0", description = "Cinemax Movie APIs Documentation"))
public class CinemaxMovieApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinemaxMovieApplication.class, args);
    }
}
