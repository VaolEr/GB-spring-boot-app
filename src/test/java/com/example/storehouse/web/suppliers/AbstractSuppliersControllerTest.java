package com.example.storehouse.web.suppliers;

import com.example.storehouse.model.Item;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.service.SuppliersService;
import com.example.storehouse.util.exception.NotFoundException;
import com.example.storehouse.web.AbstractControllerTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.example.storehouse.TestData.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import java.util.List;
import java.util.stream.Collectors;

abstract class AbstractSuppliersControllerTest extends AbstractControllerTest {

    @Value("${app.endpoints.base_path}" + "${app.endpoints.suppliers.base_url}/")
    String suppliersPath;

    @MockBean
    SuppliersService suppliersService;

    Supplier testSupplierOne,
            testSupplierTwo,
            testSupplierThree;
    List<Supplier> testSuppliers;

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
        assertNotNull(suppliersService);
    }

    @Test
    @SneakyThrows
    void getAll() {
        // Given
        List<Supplier> suppliers = testSuppliers;
        when(suppliersService.get(any())).thenReturn(suppliers);

        // When
        mvc
                .perform(get(suppliersPath)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(suppliersService).get(isNull());
    }

    @Test
    @SneakyThrows
    void getById() {
        // Given
        when(suppliersService.getById(TEST_SUPPLIER_1_ID)).thenReturn(testSupplierOne);

        // When
        mvc
                .perform(get(suppliersPath + "/{id}", TEST_SUPPLIER_1_ID)
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
        verify(suppliersService).getById(TEST_SUPPLIER_1_ID);
    }

    @Test
    @SneakyThrows
    void getByIdNotFound() {
        // Given
        int absentedSupplierId = 0;
        when(suppliersService.getById(absentedSupplierId)).thenThrow(NotFoundException.class);

        // When
        mvc
                .perform(get(suppliersPath + "/{id}", absentedSupplierId)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(suppliersService).getById(absentedSupplierId);
    }

    abstract void create();

    abstract void update();

    abstract void delete();

    void createTestEntities() {

        createItems();

        testSupplierOne = new Supplier();
        testSupplierOne.setId(TEST_SUPPLIER_1_ID);

        testSupplierTwo = new Supplier();
        testSupplierTwo.setId(TEST_SUPPLIER_2_ID);

        testSupplierThree = new Supplier();
        testSupplierThree.setId(TEST_SUPPLIER_3_ID);

        testSuppliers = List.of(testSupplierOne, testSupplierTwo, testSupplierThree).stream()
                .peek(supplier -> {
                    supplier.setName(TEST_SUPPLIERS_NAME + supplier.getId());
                    supplier.setItems(testItems);
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
