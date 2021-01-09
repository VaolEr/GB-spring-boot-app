package com.example.storehouse.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.Supplier;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//TODO add @JsonTest?
public class SuppliersUtilTests {

    static final int TEST_SUPPLIER_ID = 21;
    static final String TEST_SUPPLIER_NAME = "TestSupplier";

    Supplier testSupplier;
    SupplierTo supplierTo;

    @BeforeEach
    public void setUpTests(){

        testSupplier = new Supplier();
        testSupplier.setId(TEST_SUPPLIER_ID);
        testSupplier.setName(TEST_SUPPLIER_NAME);
        testSupplier.setItems(List.of(new Item()));

        supplierTo = SuppliersUtil.toSupplierTo(testSupplier);
    }

    @Test
    public void testToCategoryTo() {
        assertEquals(TEST_SUPPLIER_ID, supplierTo.getId());
        assertEquals(TEST_SUPPLIER_NAME, supplierTo.getName());
    }

    @Test
    public void testToCategoryTos() {
        List<Supplier> suppliers = new ArrayList<>();

        suppliers.add(testSupplier);

        List<SupplierTo> suppliersTos = SuppliersUtil.toSupplierTos(suppliers);

        assertEquals(suppliers.get(0).getId(), suppliersTos.get(0).getId());
        assertEquals(suppliers.get(0).getName(), suppliersTos.get(0).getName());
    }

    @Test
    public void testFromCategoryTo() {
        Supplier supplier = SuppliersUtil.fromSupplierTo(supplierTo);
        assertEquals(TEST_SUPPLIER_NAME, supplier.getName()); // because we use only name parameter in function
    }

}
