package com.example.storehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.storehouse.dto.CategoryTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.util.CategoriesUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

//TODO add @JsonTest?
public class CategoriesUtilTest {

    static final int ID = 21;
    static final String NAME = "TestCategory";
    static List<Item> ITEMS = new ArrayList<>();
    static {
        ITEMS.add(new Item());
    }

    @Test
    public void testToCategoryTo() {


        CategoryTo categoryTo = CategoriesUtil.toCategoryTo(new Category(ID,NAME,ITEMS));
        assertEquals(ID, categoryTo.getId());
        assertEquals(NAME, categoryTo.getName());
        //assertEquals(ITEMS,categoryTo.getItems()); //we don't have items list in categoryTo object
    }

    @Test
    public void testToCategoryTos() {
        final int ID = 21;
        final String NAME = "TestCategory";
        List<Item> ITEMS = new ArrayList<>();
        ITEMS.add(new Item());

        List<Category> categories = new ArrayList<>();

        categories.add(new Category(ID,NAME,ITEMS));

        List<CategoryTo> categoriesTos = CategoriesUtil.toCategoryTos(categories);

        assertEquals(categories.get(0).getId(), categoriesTos.get(0).getId());
        assertEquals(categories.get(0).getName(), categoriesTos.get(0).getName());
        //assertEquals(categories.get(0).getItems(), categoriesTos.get(0).getItems()); //we don't have items list in categoryTo object
    }

    @Test
    public void testFromCategoryTo() {
        final int ID = 21;
        final String NAME = "TestCategory";
        List<Item> ITEMS = new ArrayList<>();
        ITEMS.add(new Item());

        CategoryTo categoryTo = CategoriesUtil.toCategoryTo(new Category(ID,NAME,ITEMS));
        Category category = CategoriesUtil.fromCategoryTo(categoryTo);
        //assertEquals(ID, category.getId());
        assertEquals(NAME, category.getName()); // because we use only name parameter in function
        //assertEquals(ITEMS,category.getItems());
    }

}
