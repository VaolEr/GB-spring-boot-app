package com.example.storehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.storehouse.dto.CategoryTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.util.CategoriesUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//TODO add @JsonTest?
public class CategoriesUtilTest {

    static final int ID = 21;
    static final String NAME = "TestCategory";
    static List<Item> ITEMS = new ArrayList<>();
    Category testCategory;
    CategoryTo categoryTo;

    @BeforeEach
    public void setUpTests(){
        ITEMS.add(new Item());

        testCategory = new Category();
        testCategory.setId(ID);
        testCategory.setName(NAME);
        testCategory.setItems(ITEMS);

        categoryTo = CategoriesUtil.toCategoryTo(testCategory);
    }

    @Test
    public void testToCategoryTo() {
        assertEquals(ID, categoryTo.getId());
        assertEquals(NAME, categoryTo.getName());
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
        assertEquals(NAME, category.getName()); // because we use only name parameter in function
        //assertEquals(ITEMS,category.getItems());
    }

}
