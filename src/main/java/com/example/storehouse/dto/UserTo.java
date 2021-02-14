package com.example.storehouse.dto;

import com.example.storehouse.model.Role;
import com.example.storehouse.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank
    @Schema(description = "User email", example = "userlogin@mail.com")
    @Email(message = "Email should be valid!")
    String email;

    @NotNull
    @NotBlank
    @Schema(description = "User password", example = "userP@ssw0rd")
    @Min(value = 7, message = "Password should not be less than 7 symbols")
    @Max(value = 100, message = "Password should not be more than 100 symbols")
    //@JsonProperty(access = Access.WRITE_ONLY)
    String password;

    @NotNull
    @NotBlank
    @Schema(description = "User first name", example = "John")
    String firstName;

    @NotNull
    @NotBlank
    @Schema(description = "User last name", example = "Doe")
    String lastName;

    @NotNull
    //@NotBlank
    @Schema(description = "User role on the server", example = "Admin")
    Role role;

    @NotNull
    //@NotBlank
    @Schema(description = "User status on server", example = "BANNED")
    Status status;

}
