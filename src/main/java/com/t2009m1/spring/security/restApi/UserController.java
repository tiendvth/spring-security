package com.t2009m1.spring.security.restApi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @RequestMapping(method = RequestMethod.GET)
    public String hello() {
        return "Hello user!";
    }
}
