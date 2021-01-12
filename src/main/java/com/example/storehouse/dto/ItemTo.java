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

    @Schema(description = "Identifier", example = "1")
    Integer id;

    @NotNull
    @NotBlank
    @Schema(description = "Item name", example = "TheBestItem")
    String name;

    @NotNull
    @NotBlank
    @Schema(description = "Item sku", example = "#Supplier_name_code_sku")
    String sku;

    @NotNull
    @Schema(description = "Item supplier data: supplier id and supplier name", example = "{id = 1; name = TheBestSupplier}")
    SupplierTo supplier;

    @NotNull
    @Schema(description = "Item categories data: category id and category name" , example = "{id = 1; name = TheBestCategory}")
        //TODO only one category or several categories for one item???
    List<CategoryTo> categories;

    @NotNull
    @JsonProperty(value = "storehouses_balance")
    @Schema(description = "ItemStorehouse data: storehouse and quantity of item in this storehouse", example = "{id = 1; qty = 25;}")
    List<ItemStorehouseTo> itemsStorehousesTo;

}
