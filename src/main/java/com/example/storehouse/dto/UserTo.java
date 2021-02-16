package com.example.storehouse.dto;

import com.example.storehouse.model.Role;
import com.example.storehouse.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entity: User")
public class UserTo {

    @Schema(description = "Identifier", example = "1")
    Integer id;

    @NotBlank
    @Schema(description = "User email", example = "userlogin@mail.com")
    @Email(message = "Email should be valid!")
    String email;

    @NotBlank
    @Schema(description = "User password", example = "userP@ssw0rd")
    @Size(min = 7, max = 100, message = "Password length must be between 7 and 100 chars")
    //@JsonProperty(access = Access.WRITE_ONLY)
    String password;

    @NotBlank
    @Schema(description = "User first name", example = "John")
    @JsonProperty("first_name")
    String firstName;

    @NotBlank
    @Schema(description = "User last name", example = "Doe")
    @JsonProperty("last_name")
    String lastName;

    @NotNull
    @Schema(description = "User role on the server", example = "Admin")
    Role role;

    @NotNull
    @Schema(description = "User status on server", example = "BANNED")
    Status status;

}
