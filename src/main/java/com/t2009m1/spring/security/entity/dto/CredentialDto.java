package com.t2009m1.spring.security.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CredentialDto {
    private String accessToken;
    private String refreshToken;
    private Long expireTimeAt;
    private String scope;
}

