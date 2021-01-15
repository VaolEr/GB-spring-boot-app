package com.example.storehouse.dto;

import com.example.storehouse.model.Role;
import com.example.storehouse.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
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
    Integer id;

    @NotNull
    @NotBlank
    @Schema(description = "User email", example = "userlogin@mail.com")
    String email;

    @NotNull
    @NotBlank
    @Schema(description = "User password", example = "userP@ssw0rd")
    String password;

    @NotNull
    @NotBlank
    @Schema(description = "User first name", example = "UserFirstName")
    String firstName;

    @NotNull
    @NotBlank
    @Schema(description = "User last name", example = "UserLastName")
    String lastName;

    @NotNull
    @NotBlank
    @Schema(description = "User role on the server", example = "Admin")
    Role role;

    @NotNull
    @NotBlank
    @Schema(description = "User status on server", example = "BANNED")
    Status status;

}
