package com.example.storehouse.util;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.Item;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemsUtil {

    public static ItemTo toItemTo(Item item) {
        return new ItemTo(
            item.getId(),
            item.getName(),
            item.getSku()
        );
    }

}
