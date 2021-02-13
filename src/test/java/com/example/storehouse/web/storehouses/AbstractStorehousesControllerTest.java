package com.example.storehouse.web.storehouses;

import com.example.storehouse.model.Item;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.service.StorehousesService;
import com.example.storehouse.util.exception.NotFoundException;
import com.example.storehouse.web.AbstractControllerTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
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

public abstract class AbstractStorehousesControllerTest extends AbstractControllerTest {
    @Value("${app.endpoints.base_path}" + "${app.endpoints.storehouses.base_url}/")
    String storehousesPath;

    @MockBean
    StorehousesService storehousesService;

    Storehouse testStorehouseOne,
            testStorehouseTwo,
            testStorehouseThree;
    List<Storehouse> testStorehouses;

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
        assertNotNull(storehousesService);
    }

    @Test
    @SneakyThrows
    @DisplayName("Get all storehouses")
    void getAll() {
        // Given
        List<Storehouse> storehouses = testStorehouses;
        when(storehousesService.get(any())).thenReturn(storehouses);

        // When
        mvc
                .perform(get(storehousesPath)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(storehousesService).get(isNull());
    }

    @Test
    @SneakyThrows
    @DisplayName("Get storehouse by existing id")
    void getById() {
        // Given
        when(storehousesService.getById(TEST_STOREHOUSE_1_ID)).thenReturn(testStorehouseOne);

        // When
        mvc
                .perform(get(storehousesPath + "/{id}", TEST_STOREHOUSE_1_ID)
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
        verify(storehousesService).getById(TEST_STOREHOUSE_1_ID);
    }

    @Test
    @SneakyThrows
    @DisplayName("Get storehouse by not existing id")
    void getByIdNotFound() {
        // Given
        int absentedCategoryId = 0;
        when(storehousesService.getById(absentedCategoryId)).thenThrow(NotFoundException.class);

        // When
        mvc
                .perform(get(storehousesPath + "/{id}", absentedCategoryId)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(storehousesService).getById(absentedCategoryId);
    }

    abstract void create();

    abstract void update();

    abstract void delete();

    void createTestEntities() {

        createItems();

        testStorehouseOne = new Storehouse();
        testStorehouseOne.setId(TEST_STOREHOUSE_1_ID);

        testStorehouseTwo = new Storehouse();
        testStorehouseTwo.setId(TEST_STOREHOUSE_2_ID);

        testStorehouseThree = new Storehouse();
        testStorehouseThree.setId(TEST_STOREHOUSE_3_ID);

        testStorehouses = List.of(testStorehouseOne, testStorehouseTwo, testStorehouseThree).stream()
                .peek(storehouse -> {
                    storehouse.setName(TEST_CATEGORIES_NAME + storehouse.getId());
                    storehouse.setItemStorehouses(new HashSet<ItemStorehouse>(createItemStorehouses()));
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

    List<ItemStorehouse> createItemStorehouses() {
        ItemStorehouse testItemStorehouseOne = new ItemStorehouse();
        testItemStorehouseOne.setStorehouse(testStorehouseOne);
        testItemStorehouseOne.setQuantity(14);

        ItemStorehouse testItemStorehouseTwo = new ItemStorehouse();
        testItemStorehouseTwo.setStorehouse(testStorehouseTwo);
        testItemStorehouseTwo.setQuantity(18);

        ItemStorehouse testItemStorehouseThree = new ItemStorehouse();
        testItemStorehouseOne.setStorehouse(testStorehouseThree);
        testItemStorehouseOne.setQuantity(25);

        return List.of(testItemStorehouseOne, testItemStorehouseTwo, testItemStorehouseThree);
    }
}
