package com.example.storehouse.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.storehouse.dto.ItemStorehouseTo;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.Storehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//TODO add @JsonTest?
public class ItemStorehousesUtilTests {

    static final int TEST_STOREHOUSE_ID = 24;
    static final String TEST_STOREHOUSE_NAME = "TestStorehouse";

    static final int TEST_ITEMSTOREHOUSE_QTY = 25;

    ItemStorehouse testItemStorehouse;
    ItemStorehouseTo testItemStorehouseTo;
    Storehouse testStorehouse;

    @BeforeEach
    public void setUpTests(){

        testStorehouse = new Storehouse();
        testStorehouse.setId(TEST_STOREHOUSE_ID);
        testStorehouse.setName(TEST_STOREHOUSE_NAME);

        testItemStorehouse = new ItemStorehouse();
        testItemStorehouse.setItem(new Item());
        testItemStorehouse.setStorehouse(testStorehouse);
        testItemStorehouse.setQuantity(TEST_ITEMSTOREHOUSE_QTY);

        testItemStorehouseTo = ItemStorehousesUtil.toItemStorehouseTo(testItemStorehouse);

    }

    @Test
    public void testToItemStorehouseTo() {
        assertEquals(TEST_STOREHOUSE_ID, testItemStorehouseTo.getStorehouseId());
        assertEquals(TEST_ITEMSTOREHOUSE_QTY, testItemStorehouseTo.getQuantity());
    }

}
