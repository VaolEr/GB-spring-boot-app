package com.example.storehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
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
    @JsonProperty(value = "storehouses_balance")
    List<ItemStorehouseTo> itemsStorehousesTo;

}
