package com.t2009m1.spring.security.restApi;

import com.t2009m1.spring.security.entity.dto.AccountLoginDto;
import com.t2009m1.spring.security.entity.dto.AccountRegisterDto;
import com.t2009m1.spring.security.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    final AccountService accountService;

    @RequestMapping(path = "register")
    public ResponseEntity<?> register(@RequestBody AccountRegisterDto accountRegisterDto) {
    return ResponseEntity.ok(accountService.resgiter(accountRegisterDto));
    }
    public ResponseEntity<?> login(@RequestBody AccountLoginDto accountLoginDto) {
        return ResponseEntity.ok(accountService.login(accountLoginDto));
    }
    @RequestMapping(method = RequestMethod.GET)
    public String getInformation() {
        return "getInformation";
    }
}
