package com.example.storehouse.dto;

import com.example.storehouse.model.Role;
import com.example.storehouse.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserTo {
    Integer id;

    @NotNull
    @NotBlank
    String email;

    @NotNull
    @NotBlank
    String password;

    @NotNull
    @NotBlank
    String firstName;

    @NotNull
    @NotBlank
    String lastName;

    @NotNull
    @NotBlank
    Role role;

    @NotNull
    @NotBlank
    Status status;

}
