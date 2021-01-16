package com.example.storehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Entity: Authentication Request")
public class AuthenticationRequestTo {

    @Schema(description = "User email as login.", example = "userlogin@mail.com")
    private String email;

    @Schema(description = "User password.", example = "userP@ssw0rd")
    private String password;
}
