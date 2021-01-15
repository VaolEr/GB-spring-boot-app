package com.example.storehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Entity: RestResponse")
public class RestResponseTo<T> {

    @Schema(description = "Response status")
    private String responseStatus;

    @Schema(description = "Response error message")
    private String errorMessage;

    @Schema(description = "Response data")
    private T data;

}
