package com.t2009m1.spring.security.config;

import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.t2009m1.spring.security.entity.dto.AccountLoginDto;
import com.t2009m1.spring.security.entity.dto.CredentialDto;
import com.t2009m1.spring.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    final AuthenticationManager authenticationManager;

    public Authentication MyAuthenticationFilter(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            String jsonData = request.getReader().lines().collect(Collectors.joining());
            Gson gson = new Gson();
            AccountLoginDto accountLoginDto = gson.fromJson(jsonData, AccountLoginDto.class);
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(accountLoginDto.getUsername(), accountLoginDto.getPassword());
            return authenticationManager.authenticate(userToken);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        User user = (User) authResult.getPrincipal();
        String accessToken = JwtUtil.generateToken(user.getUsername(), user.getAuthorities().iterator().next().getAuthority(), request.getRequestURL().toString(), JwtUtil.ONE_DAY * 7);
        String refreshToken = JwtUtil.generateToken(user.getUsername(), user.getAuthorities().iterator().next().getAuthority(), request.getRequestURL().toString(), JwtUtil.ONE_DAY * 14);
        CredentialDto credentialDto = new CredentialDto(accessToken, refreshToken, (long) (JwtUtil.ONE_DAY * 7), "JwtUtil.ONE_DAY * 14");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(credentialDto));

        log.info("successfulAuthentication");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        HashMap<String, String> error = new HashMap<>();
        error.put("error", "Authentication failed");
    }

}

