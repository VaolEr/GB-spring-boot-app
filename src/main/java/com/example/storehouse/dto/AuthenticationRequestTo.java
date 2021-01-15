package com.example.storehouse.dto;

import lombok.Data;

@Data
public class AuthenticationRequestTo {
    private String email;
    private String password;
}
