package com.example.storehouse;

import com.example.storehouse.model.Role;
import com.example.storehouse.model.Status;

public class TestData {

    public static final int TEST_CATEGORY_ID = 21;
    public static final String TEST_CATEGORY_NAME = "TestCategory";

    public static final int TEST_CATEGORY_1_ID = 101;
    public static final int TEST_CATEGORY_2_ID = 201;
    public static final int TEST_CATEGORY_3_ID = 301;
    public static final String TEST_CATEGORIES_NAME = "Test category #";

    public static final int TEST_SUPPLIER_ID = 31;
    public static final String TEST_SUPPLIER_NAME = "TestSupplier";

    public static final int TEST_STOREHOUSE_1_ID = 41;
    public static final String TEST_STOREHOUSE_1_NAME = "TestStorehouse 1";
    public static final int TEST_STOREHOUSE_2_ID = 51;
    public static final String TEST_STOREHOUSE_2_NAME = "TestStorehouse 2";


    public static final String TEST_ITEMS_SKU = "#test-sku";
    public static final String TEST_ITEMS_NAME = "Test item # ";
    public static final int TEST_ITEM_1_ID = 111;
    public static final int TEST_ITEM_2_ID = 211;
    public static final int TEST_ITEM_3_ID = 311;

    public static final int TEST_UNIT_ID = 81;
    public static final String TEST_UNIT_NAME = "шт";


    public static final int TEST_ADMIN_ID = 1000;
    public static final String TEST_ADMIN_EMAIL = "admin@mail.com";
    public static final String TEST_ADMIN_PASSWORD = "admin-password";
    public static final String TEST_ADMIN_FIRST_NAME = "Administrator";
    public static final String TEST_ADMIN_LAST_NAME = "ADMINISTRATOR";
    public static final Role TEST_ADMIN_ROLE = Role.ADMIN;
    public static final Status TEST_ADMIN_STATUS = Status.ACTIVE;

    public static final int TEST_USER_ID = 1001;
    public static final String TEST_USER_EMAIL = "user@mail.com";
    public static final String TEST_USER_PASSWORD = "user-password";
    public static final String TEST_USER_FIRST_NAME = "User";
    public static final String TEST_USER_LAST_NAME = "User";
    public static final Role TEST_USER_ROLE = Role.USER;
    public static final Status TEST_USER_STATUS = Status.ACTIVE;

}
