package com.t2009m1.spring.security.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AccountLoginDto {
    private String username;
    private String password;
}

