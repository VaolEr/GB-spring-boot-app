package com.example.storehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entity: ItemStorehouse")
public class ItemStorehouseTo {

    @NotNull
    @JsonProperty(value = "id")
    @Schema(description = "Storehouse id", example = "1")
    Integer storehouseId;

    @NotNull
    @Min(value = 1)
    @Schema(description = "Quantity of Items stored in storehouseId Storehouse", example = "25")
    Integer quantity;

}
