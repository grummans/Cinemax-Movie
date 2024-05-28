package com.dropchat.cinemaxmovie.configuration;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
