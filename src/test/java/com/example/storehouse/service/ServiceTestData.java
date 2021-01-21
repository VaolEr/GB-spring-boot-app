package com.example.storehouse.service;

import com.example.storehouse.model.Role;
import com.example.storehouse.model.Status;

public class ServiceTestData {

    static final int TEST_CATEGORY_ID = 21;
    static final String TEST_CATEGORY_NAME = "TestCategory";

    static final int TEST_SUPPLIER_ID = 31;
    static final String TEST_SUPPLIER_NAME = "TestSupplier";

    static final int TEST_STOREHOUSE_1_ID = 41;
    static final String TEST_STOREHOUSE_1_NAME = "TestStorehouse 1";
    static final int TEST_STOREHOUSE_2_ID = 51;
    static final String TEST_STOREHOUSE_2_NAME = "TestStorehouse 2";


    static final String TEST_ITEMS_SKU = "#test-sku";
    static final String TEST_ITEMS_NAME = "Test item # ";
    static final int TEST_ITEM_1_ID = 111;
    static final int TEST_ITEM_2_ID = 211;
    static final int TEST_ITEM_3_ID = 311;

    static final int TEST_UNIT_ID = 81;
    static final String TEST_UNIT_NAME = "шт";

    static final int TEST_USER_ID = 91;
    static final String TEST_USER_EMAIL = "testEmail.test.com";
    static final String TEST_USER_PASSWORD = "testPassword";
    static final String TEST_USER_FIRST_NAME = "TestUserName";
    static final String TEST_USER_LAST_NAME = "TestUserLastName";
    static final Role TEST_USER_ROLE = Role.ADMIN;
    static final Status TEST_USER_STATUS = Status.ACTIVE;

}
