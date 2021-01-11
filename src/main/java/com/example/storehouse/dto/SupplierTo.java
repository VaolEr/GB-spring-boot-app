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
@Schema(description = "Entity: Supplier")
public class SupplierTo {

    @Schema(description = "Identifier")
    Integer id;

    @NotNull
    @NotBlank
    @Schema(description = "Supplier name")
    String name;

}
