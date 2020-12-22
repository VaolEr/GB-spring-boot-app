package com.example.storehouse.util;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.Item;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

//@UtilityClass
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemsUtil {

    // NOTE: не забыть проверить необходимость этого метода, если будет исп. EntityGraph
    public static ItemTo toItemTo(Item item) {
        return ItemTo
            .builder()
            .id(item.getId())
            .name(item.getName())
            .sku(item.getSku())
            .categoryId(item.getCategory().getId())
            .supplierId(item.getSupplier().getId())
            .build();
    }

    public static List<ItemTo> toItemTos(List<Item> items) {
        return items.stream().map(ItemsUtil::toItemTo).collect(Collectors.toList());
    }

    public static Item fromItemTo(ItemTo itemTo) {
        Item newItem = new Item();
        newItem.setName(itemTo.getName());
        newItem.setSku(itemTo.getSku());
        return newItem;
    }

}
