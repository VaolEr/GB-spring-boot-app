package com.example.storehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResponseTo<T> {

    private String responseStatus;

    private String errorMessage;

    private T data;

}
