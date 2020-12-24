package com.example.storehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
public class SupplierTo {

    // Этот ТО нам пригодится при создании/обновлении Supplier

    Integer id;

    @NotNull
    @NotBlank
    String name;

}
