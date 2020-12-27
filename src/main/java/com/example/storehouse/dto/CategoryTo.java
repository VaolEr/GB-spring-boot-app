package com.example.storehouse.dto;

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
public class CategoryTo {

    // Этот ТО нам пригодится при создании/обновлении Category

    Integer id;

    @NotNull
    @NotBlank
    String name;

}
