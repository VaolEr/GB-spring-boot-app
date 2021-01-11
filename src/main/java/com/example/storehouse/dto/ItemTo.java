package com.example.storehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entity: Item")
public class ItemTo {

    Integer id;

    @NotNull
    @NotBlank
    @Schema(description = "Item name")
    String name;

    @NotNull
    @NotBlank
    @Schema(description = "Item sku")
    String sku;

    @NotNull
    @Schema(description = "Item supplier data: supplier id and supplier name")
    SupplierTo supplier;

    @NotNull
    @Schema(description = "Item categories data: category id and category name")
        //TODO only one category or several categories for one item???
    List<CategoryTo> categories;

    @NotNull
    @JsonProperty(value = "storehouses_balance")
    @Schema(description = "ItemStorehouse data: storehouse and quantity of item in this storehouse")
    List<ItemStorehouseTo> itemsStorehousesTo;

}
