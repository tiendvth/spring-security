package com.t2009m1.spring.security.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "credentials")
public class Credential {
    @Id
    private String accessToken;
    private String refreshToken;
    private Long expireTime;
    private String scope;
}
