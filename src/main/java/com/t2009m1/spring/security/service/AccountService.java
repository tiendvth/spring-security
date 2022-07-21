package com.t2009m1.spring.security.service;

import com.t2009m1.spring.security.entity.Account;
import com.t2009m1.spring.security.entity.Credential;
import com.t2009m1.spring.security.entity.dto.AccountLoginDto;
import com.t2009m1.spring.security.entity.dto.AccountRegisterDto;
import com.t2009m1.spring.security.repository.AccountRepository;
import com.t2009m1.spring.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    @Autowired
    final AccountRepository accountRepository;
    @Autowired
    private final BCryptPasswordEncoder  bCryptPasswordEncoder;

    public AccountRegisterDto  resgiter(AccountRegisterDto accountRegisterDto) {
        Optional<Account> optionalAccount = accountRepository.findAccountByUsername(accountRegisterDto.getUsername());
        if (optionalAccount.isPresent()) {
            return null;
        }
        Account account = Account.builder()
                .username(accountRegisterDto.getUsername())
                .passwordHash(bCryptPasswordEncoder.encode(accountRegisterDto.getPasswordHash()))
                .role(accountRegisterDto.getRole())
                .build();
         accountRepository.save(account);
         accountRegisterDto.setId(account.getId());
        return accountRegisterDto;
        // TODO
    }
    public Credential login(AccountLoginDto accountLoginDto) {
//        User user = (User) loadUserByUsername(accountRegisterDto.getUsername());
        Optional<Account> optionalAccount = accountRepository.findAccountByUsername(accountLoginDto.getUsername());
        if (optionalAccount.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Account account = optionalAccount.get();
        boolean isMasch = bCryptPasswordEncoder.matches(accountLoginDto.getPassword(), account.getPasswordHash());
        if (isMasch){
            int expireAfter = 7;
            String accessToken = JwtUtil.generateTokenByAccount(account,expireAfter * 60 * 60 * 10000 * 7 * 24);
            String refreshToken = JwtUtil.generateTokenByAccount(account,expireAfter * 60 * 60 * 10000 * 7 * 24);
            Credential credential = new Credential();
            credential.setAccessToken(accessToken);
            credential.setRefreshToken(refreshToken);
            credential.setExpireTime((long) expireAfter);
            return credential;

        } else {
            throw new RuntimeException("Login failed");
        }
        // TODO
    }
    public void logout(AccountRegisterDto accountRegisterDto) {
        // TODO
    }
    public void getInformation(AccountRegisterDto accountRegisterDto) {
        // TODO
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Account> optionalAccount = accountRepository.findAccountByUsername(username);
       if (!optionalAccount.isPresent()) {
           throw new UsernameNotFoundException("User not found");

         }
       Account account = optionalAccount.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(account.getRole() == 1 ? "USER" : "ADMIN");
        User user = new User(account.getUsername(), account.getPasswordHash(), authorities);

        return user;
    }
}
