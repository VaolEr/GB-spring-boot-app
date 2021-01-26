package com.example.storehouse.dto;

import com.example.storehouse.web.validation.ValidationGroups.Create;
import com.example.storehouse.web.validation.ValidationGroups.Replace;
import com.example.storehouse.web.validation.ValidationGroups.Delete;
import com.example.storehouse.web.validation.ValidationGroups.Update;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Schema(description = "Item SKU", example = "#Supplier_name_code_sku")
    String sku;

    @NotNull
    @Schema(description = "Item supplier data: supplier id and name", example = "{\"id\": 1000, \"name\": \"TheBestSupplier\"}")
    SupplierTo supplier;

    @NotNull
    @Schema(description = "Item categories data: category id and name", example = "[{\"id\": 1000, \"name\": \"TheBestCategory\"}]")
    //TODO only one category or several categories for one item???
    List<CategoryTo> categories;

    @NotNull
    @Schema(description = "Item unit", example = "pcs|шт")
    UnitTo unit;

    @NotNull
    @JsonProperty(value = "storehouses_balance")
    @Schema(description = "ItemStorehouse data: storehouse and quantity of item in this storehouse",
        example = "[{\"id\": 1000, \"quantity\": 25}, {\"id\": 1001, \"quantity\": 15}]")
    List<ItemStorehouseTo> itemsStorehouses;

    //@NotNull
    @Null(
        groups = Create.class
    )
    @NotNull(
        groups = { Update.class, Delete.class, Replace.class }
    )
    @Schema(description = "Total quantity of item stored in all storehouses", example = "101")
    Integer totalQty;
}
