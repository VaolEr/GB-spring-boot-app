package com.example.storehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Value;

@Value
@Schema(description = "Entity: Authentication Request")
public class AuthenticationRequestTo {

    @Schema(description = "User email as login.", example = "user@mail.com")
    @Email
    String email;

    @NotBlank
    @Schema(description = "User password.", example = "user-password")
    String password;

}
