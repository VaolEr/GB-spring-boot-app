package com.example.storehouse.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemTo {

    // Этот ТО нам пригодится при создании/обновлении Item

    Integer id;

    @NotNull
    @NotBlank
    String name;

    @NotNull
    @NotBlank
    String sku;

    @NotNull
    Integer supplierId;

    @NotNull
    Integer categoryId;

    @NotNull
    Integer storehouseId;

    @NotNull
    Integer quantity;

    public ItemTo(Integer id,
                  @NotNull @NotBlank String name,
                  @NotNull @NotBlank String sku) {
        this.id = id;
        this.name = name;
        this.sku = sku;
    }
}
