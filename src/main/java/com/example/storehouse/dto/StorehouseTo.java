package com.example.storehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorehouseTo {

    Integer id;

    @NotNull
    @NotBlank
    String name;

    @NotNull
    @JsonProperty(value = "storehouses_balance")
    List<ItemStorehouseTo> itemsStorehousesTo;

    public void setName(String name) {
        this.name = name;
    }
}
