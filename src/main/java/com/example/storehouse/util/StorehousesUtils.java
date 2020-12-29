package com.example.storehouse.util;

import com.example.storehouse.dto.StorehouseTo;
import com.example.storehouse.model.Storehouse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StorehousesUtils {

    public static Storehouse fromStorehouseTo(StorehouseTo storehouseTo){
        Storehouse newStorehouse = new Storehouse();
        newStorehouse.id = storehouseTo.getId();
        newStorehouse.name = storehouseTo.getName();
        return newStorehouse;
    }
}
