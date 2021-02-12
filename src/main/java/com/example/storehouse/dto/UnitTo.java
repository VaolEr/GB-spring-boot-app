package com.example.storehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entity: Unit")
public class UnitTo {

    @Schema(description = "Identifier", example = "1000")
    Integer id;

    @NotNull
    @NotBlank
    @Schema(description = "Unit name", example = "pcs|шт")
    String name;

}
