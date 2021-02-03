package com.example.storehouse.web.items;

import static com.example.storehouse.TestData.TEST_CATEGORY_ID;
import static com.example.storehouse.TestData.TEST_CATEGORY_NAME;
import static com.example.storehouse.TestData.TEST_ITEMS_NAME;
import static com.example.storehouse.TestData.TEST_ITEMS_SKU;
import static com.example.storehouse.TestData.TEST_ITEM_1_ID;
import static com.example.storehouse.TestData.TEST_ITEM_2_ID;
import static com.example.storehouse.TestData.TEST_ITEM_3_ID;
import static com.example.storehouse.TestData.TEST_STOREHOUSE_1_ID;
import static com.example.storehouse.TestData.TEST_STOREHOUSE_1_NAME;
import static com.example.storehouse.TestData.TEST_STOREHOUSE_2_ID;
import static com.example.storehouse.TestData.TEST_STOREHOUSE_2_NAME;
import static com.example.storehouse.TestData.TEST_SUPPLIER_ID;
import static com.example.storehouse.TestData.TEST_SUPPLIER_NAME;
import static com.example.storehouse.TestData.TEST_UNIT_ID;
import static com.example.storehouse.TestData.TEST_UNIT_NAME;
import static com.example.storehouse.util.ItemsUtil.toItemTos;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.model.Unit;
import com.example.storehouse.service.ItemsService;
import com.example.storehouse.util.exception.NotFoundException;
import com.example.storehouse.web.AbstractControllerTest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public abstract class AbstractItemsControllerTest extends AbstractControllerTest {

    @Value("${app.endpoints.base_path}" + "${app.endpoints.items.base_url}/")
    String itemsPath;

    @MockBean
    ItemsService itemsService;

    Pageable unpaged;
    Item testItemOne,
        testItemTwo,
        testItemThree;
    List<Item> testItems;
    Category testCategory;
    Supplier testSupplier;
    Unit testUnit;
    Storehouse testStorehouseOne,
        testStorehouseTwo;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        createTestEntities();
        // Results be returned with default Pageable settings
        unpaged = Pageable.unpaged();
    }

    @Override
    @Test
    protected void contextLoads() {
        super.contextLoads();
        assertNotNull(itemsService);
    }

    @Test
    @SneakyThrows
    void getAllUnpaged() {
        // Given
        Page<ItemTo> pagedItemTos = new PageImpl<>(toItemTos(testItems), unpaged, testItems.size());
        when(itemsService.get(isA(Pageable.class), any())).thenReturn(pagedItemTos);

        // When
        mvc
            .perform(get(itemsPath)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(itemsService).get(isA(Pageable.class), isNull());
    }

    @Disabled
    @Test
    @SneakyThrows
    void getAllPaged() {

    }

    @Test
    @SneakyThrows
    void getById() {
        // Given
        testItemOne.setItemStorehouses(createItemStorehouses().toArray(new ItemStorehouse[2]));
        //ItemTo returnedItem = toItemToWithBalance(testItemOne);
        when(itemsService.getById(TEST_ITEM_1_ID)).thenReturn(testItemOne);

        // When
        mvc
            .perform(get(itemsPath + "/{id}", TEST_ITEM_1_ID)
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
        verify(itemsService).getById(TEST_ITEM_1_ID);
    }

    @Test
    @SneakyThrows
    void getByIdNotFound() {
        // Given
        int absentedItemId = 0;
        when(itemsService.getById(absentedItemId)).thenThrow(NotFoundException.class);

        // When
        mvc
            .perform(get(itemsPath + "/{id}", absentedItemId)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(itemsService).getById(absentedItemId);
    }

    @Disabled
    @Test
    @SneakyThrows
    void getByName() {
        // unpaged
    }

    abstract void create();

    abstract void update();

    abstract void delete();

    void createTestEntities() {
        testCategory = new Category();
        testCategory.setId(TEST_CATEGORY_ID);
        testCategory.setName(TEST_CATEGORY_NAME);

        testSupplier = new Supplier();
        testSupplier.setId(TEST_SUPPLIER_ID);
        testSupplier.setName(TEST_SUPPLIER_NAME);

        testUnit = new Unit();
        testUnit.setId(TEST_UNIT_ID);
        testUnit.setName(TEST_UNIT_NAME);

        testStorehouseOne = new Storehouse();
        testStorehouseOne.setId(TEST_STOREHOUSE_1_ID);
        testStorehouseOne.setName(TEST_STOREHOUSE_1_NAME);

        testStorehouseTwo = new Storehouse();
        testStorehouseTwo.setId(TEST_STOREHOUSE_2_ID);
        testStorehouseTwo.setName(TEST_STOREHOUSE_2_NAME);

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
                item.setCategory(testCategory);
                item.setSupplier(testSupplier);
                item.setUnit(testUnit);
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

        return List.of(testItemStorehouseOne, testItemStorehouseTwo);
    }

}
