package com.example.storehouse.util;

import com.example.storehouse.dto.ItemStorehouseTo;
import com.example.storehouse.model.ItemStorehouse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemStorehousesUtil {

    public static ItemStorehouseTo toItemStorehouseTo(ItemStorehouse itemStorehouse) {
        return new ItemStorehouseTo(itemStorehouse.getStorehouse().getId(), itemStorehouse.getQuantity());
    }

}
