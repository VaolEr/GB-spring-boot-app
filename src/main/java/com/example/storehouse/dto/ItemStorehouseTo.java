package com.example.storehouse.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemStorehouseTo {

    @NotNull
    Integer storehouseId;

    @NotNull
    @Min(value = 1)
    Integer quantity;

}
