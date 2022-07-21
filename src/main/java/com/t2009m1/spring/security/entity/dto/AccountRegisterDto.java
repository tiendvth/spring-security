package com.t2009m1.spring.security.entity.dto;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AccountRegisterDto {
    private long id;
    private String username;
    private String passwordHash;
    private String confimPassword;
    private int role;
}
