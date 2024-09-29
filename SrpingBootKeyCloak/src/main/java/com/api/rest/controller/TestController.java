package com.api.rest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello-1")
    @PreAuthorize("hasRole('admin_client_role')")
    public String helloAdmin(){
        return "Hello Spring boot with keycloak - ADMIN";
    }

    @GetMapping("/hello-2")
    @PreAuthorize("hasRole('user_client_role') or hasRole('admin_client_role')")
    public String helloUser(){
        return "Hello Spring boot with keycloak - USER";
    }
}
