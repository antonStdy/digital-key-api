package com.ispk.digitalkeyapi.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RegistrationCredentialsDto {

    @Email
    private String email;

    private String password;
}
