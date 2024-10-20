package com.ebaad.SpringSecurityProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String defaultPage() {
        return "Welcome to Default Page!";
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to Home Page!";
    }

    @GetMapping("/user/home")
    public String user() {
        return "Welcome to User Home Page!";
    }

    @GetMapping("/admin/home")
    public String admin() {
        return "Welcome to Admin Home Page!";
    }
}
