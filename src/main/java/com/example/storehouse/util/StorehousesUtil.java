package com.example.storehouse.util;

import com.example.storehouse.dto.StorehouseTo;

import com.example.storehouse.model.Storehouse;
import com.example.storehouse.model.Supplier;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StorehousesUtil {

    public static StorehouseTo toStorehouseTo(Storehouse storehouse) {
        return StorehouseTo
            .builder()
            .id(storehouse.getId())
            .name(storehouse.getName())
            .build();
    }

    public static List<StorehouseTo> toStorehouseTos(List<Storehouse> storehouses) {
        return storehouses.stream().map(StorehousesUtil::toStorehouseTo).collect(Collectors.toList());
    }

    public static Storehouse fromStorehouseTo(StorehouseTo storehouseTo) {
        Storehouse newStorehouse = new Storehouse();
        newStorehouse.setName(storehouseTo.getName());
        return newStorehouse;
    }

}
