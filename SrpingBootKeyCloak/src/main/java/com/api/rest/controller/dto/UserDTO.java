package com.api.rest.controller.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Set;


@Value
@RequiredArgsConstructor
@Builder
public class UserDTO {

    private String username;
    private String email;
    private String  firtName;
    private String lastName;
    private String password;
    private Set<String> roles;
}
