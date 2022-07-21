package com.t2009m1.spring.security.service;

import com.t2009m1.spring.security.T2009m1SpringSecurityApplication;
import com.t2009m1.spring.security.entity.dto.AccountRegisterDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = T2009m1SpringSecurityApplication.class)
class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @Test
    void resgiter() {
        AccountRegisterDto accountRegisterDto = new AccountRegisterDto();
        accountRegisterDto.setUsername("tien");
        accountRegisterDto.setPasswordHash("123456");
        accountRegisterDto.setRole(1);
        AccountRegisterDto afterCreate = accountService.resgiter(accountRegisterDto);
        System.out.println(afterCreate);
    }

    @Test
    void login() {
    }

    @Test
    void getInformation() {
    }

    @Test
    void loadUserByUsername() {
    }
}