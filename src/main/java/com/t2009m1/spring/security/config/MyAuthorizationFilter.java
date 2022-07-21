package com.t2009m1.spring.security.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.t2009m1.spring.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class MyAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected  void doFilterInternal(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.FilterChain filterChain) throws java.io.IOException, javax.servlet.ServletException {
        System.out.println("MyAuthorizationFilter");
        filterChain.doFilter(request, response);
        try {
            logger.info("MyAuthorizationFilter");
            String fullToken = request.getHeader("Authorization");
            String token = fullToken.replace("Bearer ", "").trim();
            DecodedJWT decodedJWT = JwtUtil.getDecodedJwt(token);
            String accountId = decodedJWT.getSubject();
            String username = decodedJWT.getClaim("username").asString();
            String role = decodedJWT.getClaim("role").asString();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));
            logger.info("Role: " + role);
            UsernamePasswordAuthenticationToken usernameToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(usernameToken);
            System.out.println(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

}
