package com.t2009m1.spring.security.config;

import com.t2009m1.spring.security.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
final AccountService accountService;
final PasswordEncoder passwordEncoder;



    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder);
    }

    @Bean



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        MyAuthenticationFilter authenticationFilter = new MyAuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/api/v1/account/login");
        http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers("/api/v1/hello").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/admin/user").hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/api/v1/admin/admin").hasAnyAuthority("ADMIN");
        http.addFilterBefore(new MyAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}



