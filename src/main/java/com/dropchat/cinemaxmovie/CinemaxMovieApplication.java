package com.dropchat.cinemaxmovie;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Cinemax Movie API",
                version = "1.0",
                description = "Cinemax Movie APIs Documentation"
        )
)
public class CinemaxMovieApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinemaxMovieApplication.class, args);
    }

}
