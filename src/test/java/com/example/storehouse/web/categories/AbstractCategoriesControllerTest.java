package com.example.storehouse.web.categories;

import com.example.storehouse.model.*;
import com.example.storehouse.service.CategoriesService;
import com.example.storehouse.util.exception.NotFoundException;
import com.example.storehouse.web.AbstractControllerTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.storehouse.TestData.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public abstract class AbstractCategoriesControllerTest extends AbstractControllerTest {
    @Value("${app.endpoints.base_path}" + "${app.endpoints.categories.base_url}/")
    String categoriesPath;

    @MockBean
    CategoriesService categoriesService;

    Category testCategoryOne,
            testCategoryTwo,
            testCategoryThree;
    List<Category> testCategories;

    Item testItemOne,
         testItemTwo,
         testItemThree;
    List<Item> testItems;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        createTestEntities();
    }

    @Override
    @Test
    protected void contextLoads() {
        super.contextLoads();
        assertNotNull(categoriesService);
    }

    @Test
    @SneakyThrows
    void getAll() {
        // Given
        List<Category> categories = testCategories;
        when(categoriesService.get(any())).thenReturn(categories);

        // When
        mvc
                .perform(get(categoriesPath)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(categoriesService).get(isNull());
    }

    @Test
    @SneakyThrows
    void getById() {
        // Given
        when(categoriesService.getById(TEST_CATEGORY_1_ID)).thenReturn(testCategoryOne);

        // When
        mvc
                .perform(get(categoriesPath + "/{id}", TEST_CATEGORY_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isNotEmpty())
        // TODO поправить проверку содержимого
        //.andExpect(jsonPath("$.data").value(objectMapper.writeValueAsString(returnedItem)))
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(categoriesService).getById(TEST_CATEGORY_1_ID);
    }

    @Test
    @SneakyThrows
    void getByIdNotFound() {
        // Given
        int absentedCategoryId = 0;
        when(categoriesService.getById(absentedCategoryId)).thenThrow(NotFoundException.class);

        // When
        mvc
                .perform(get(categoriesPath + "/{id}", absentedCategoryId)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(categoriesService).getById(absentedCategoryId);
    }

    abstract void create();

    abstract void update();

    abstract void delete();

    void createTestEntities() {

        createItems();

        testCategoryOne = new Category();
        testCategoryOne.setId(TEST_CATEGORY_1_ID);

        testCategoryTwo = new Category();
        testCategoryTwo.setId(TEST_CATEGORY_2_ID);

        testCategoryThree = new Category();
        testCategoryThree.setId(TEST_CATEGORY_3_ID);

        testCategories = List.of(testCategoryOne, testCategoryTwo, testCategoryThree).stream()
                .peek(category -> {
                    category.setName(TEST_CATEGORIES_NAME + category.getId());
                    category.setItems(testItems);
                })
                .collect(Collectors.toList());

    }

    void createItems() {
        testItemOne = new Item();
        testItemOne.setId(TEST_ITEM_1_ID);

        testItemTwo = new Item();
        testItemTwo.setId(TEST_ITEM_2_ID);

        testItemThree = new Item();
        testItemThree.setId(TEST_ITEM_3_ID);

        testItems = List.of(testItemOne, testItemTwo, testItemThree).stream()
                .peek(item -> {
                    item.setName(TEST_ITEMS_NAME + item.getId());
                    item.setSku(TEST_ITEMS_SKU);
                })
                .collect(Collectors.toList());

    }
}