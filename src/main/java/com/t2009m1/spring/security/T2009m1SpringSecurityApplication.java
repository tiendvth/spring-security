package com.t2009m1.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class T2009m1SpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(T2009m1SpringSecurityApplication.class, args);
    }

}
