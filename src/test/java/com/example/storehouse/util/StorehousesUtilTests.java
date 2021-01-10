package com.example.storehouse.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.storehouse.dto.StorehouseTo;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.Storehouse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//TODO add @JsonTest?
public class StorehousesUtilTests {

    static final int TEST_STOREHOUSE_ID = 21;
    static final String TEST_STOREHOUSE_NAME = "TestStorehouse";

    Storehouse testStorehouse;
    StorehouseTo storehouseTo;

    @BeforeEach
    public void setUpTests(){

        testStorehouse = new Storehouse();
        testStorehouse.setId(TEST_STOREHOUSE_ID);
        testStorehouse.setName(TEST_STOREHOUSE_NAME);
        testStorehouse.setItemStorehouses(Set.of(new ItemStorehouse()));

        storehouseTo = StorehousesUtil.toStorehouseTo(testStorehouse);
    }

    @Test
    public void testToStorehouseTo() {
        assertEquals(TEST_STOREHOUSE_ID, storehouseTo.getId());
        assertEquals(TEST_STOREHOUSE_NAME, storehouseTo.getName());
    }

    @Test
    public void testToStorehouseTos() {
        List<Storehouse> storehouses = new ArrayList<>();

        storehouses.add(testStorehouse);

        List<StorehouseTo> storehousesTos = StorehousesUtil.toStorehouseTos(storehouses);

        assertEquals(storehouses.get(0).getId(), storehousesTos.get(0).getId());
        assertEquals(storehouses.get(0).getName(), storehousesTos.get(0).getName());
    }

    @Test
    public void testFromStorehouseTo() {
        Storehouse storehouse = StorehousesUtil.fromStorehouseTo(storehouseTo);
        assertEquals(TEST_STOREHOUSE_NAME, storehouse.getName()); // because we use only name parameter in function
    }

}
