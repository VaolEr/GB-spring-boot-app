package com.example.storehouse.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemStorehouseTo {

    @NotNull
    Integer storehouseId;

    @NotNull
    @Min(value = 1)
    Integer quantity;

}
