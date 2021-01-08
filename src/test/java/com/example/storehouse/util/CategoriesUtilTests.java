package com.example.storehouse.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.storehouse.dto.CategoryTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//TODO add @JsonTest?
public class CategoriesUtilTests {

    static final int TEST_CATEGORY_ID = 21;
    static final String TEST_CATEGORY_NAME = "TestCategory";

    Category testCategory;
    CategoryTo categoryTo;

    @BeforeEach
    public void setUpTests(){

        testCategory = new Category();
        testCategory.setId(TEST_CATEGORY_ID);
        testCategory.setName(TEST_CATEGORY_NAME);
        testCategory.setItems(List.of(new Item()));

        categoryTo = CategoriesUtil.toCategoryTo(testCategory);
    }

    @Test
    public void testToCategoryTo() {
        assertEquals(TEST_CATEGORY_ID, categoryTo.getId());
        assertEquals(TEST_CATEGORY_NAME, categoryTo.getName());
        //assertEquals(ITEMS,categoryTo.getItems()); //we don't have items list in categoryTo object
    }

    @Test
    public void testToCategoryTos() {
        List<Category> categories = new ArrayList<>();

        categories.add(testCategory);

        List<CategoryTo> categoriesTos = CategoriesUtil.toCategoryTos(categories);

        assertEquals(categories.get(0).getId(), categoriesTos.get(0).getId());
        assertEquals(categories.get(0).getName(), categoriesTos.get(0).getName());
        //assertEquals(categories.get(0).getItems(), categoriesTos.get(0).getItems()); //we don't have items list in categoryTo object
    }

    @Test
    public void testFromCategoryTo() {
        Category category = CategoriesUtil.fromCategoryTo(categoryTo);
        //assertEquals(ID, category.getId());
        assertEquals(TEST_CATEGORY_NAME, category.getName()); // because we use only name parameter in function
        //assertEquals(ITEMS,category.getItems());
    }

}
