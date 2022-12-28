package com.ispk.digitalkeyapi.auth.dto;

import lombok.Data;

@Data
public class LoginCredentialsDto {
    private String email;
    private String password;
}
