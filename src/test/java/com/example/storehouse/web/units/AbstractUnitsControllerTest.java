package com.example.storehouse.web.units;

import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.Unit;
import com.example.storehouse.service.CategoriesService;
import com.example.storehouse.service.UnitsService;
import com.example.storehouse.util.exception.NotFoundException;
import com.example.storehouse.web.AbstractControllerTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

public abstract class AbstractUnitsControllerTest extends AbstractControllerTest {
    @Value("${app.endpoints.base_path}" + "${app.endpoints.units.base_url}/")
    String unitsPath;

    @MockBean
    UnitsService unitsService;

    Unit testUnitOne,
            testUnitTwo,
            testUnitThree;
    List<Unit> testUnits;

//    Category testCategoryOne,
//            testCategoryTwo,
//            testCategoryThree;
//    List<Category> testCategories;

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
        assertNotNull(unitsService);
    }

    @Test
    @SneakyThrows
    @DisplayName("Get all units")
    void getAll() {
        // Given
        List<Unit> units = testUnits;
        when(unitsService.get(any())).thenReturn(units);

        // When
        mvc
                .perform(get(unitsPath)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(unitsService).get(isNull());
    }

    @Test
    @SneakyThrows
    @DisplayName("Get category by existing id")
    void getById() {
        // Given
        when(unitsService.getById(TEST_UNIT_1_ID)).thenReturn(testUnitOne);

        // When
        mvc
                .perform(get(unitsPath + "/{id}", TEST_UNIT_1_ID)
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
        verify(unitsService).getById(TEST_UNIT_1_ID);
    }

    @Test
    @SneakyThrows
    @DisplayName("Get category by not existing id")
    void getByIdNotFound() {
        // Given
        int absentedUnitId = 0;
        when(unitsService.getById(absentedUnitId)).thenThrow(NotFoundException.class);

        // When
        mvc
                .perform(get(unitsPath + "/{id}", absentedUnitId)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(unitsService).getById(absentedUnitId);
    }

    abstract void create();

    abstract void update();

    abstract void delete();

    void createTestEntities() {

        createItems();

        testUnitOne = new Unit();
        testUnitOne.setId(TEST_UNIT_1_ID);

        testUnitTwo = new Unit();
        testUnitTwo.setId(TEST_UNIT_2_ID);

        testUnitThree = new Unit();
        testUnitThree.setId(TEST_UNIT_3_ID);

        testUnits = List.of(testUnitOne, testUnitTwo, testUnitThree).stream()
                .peek(unit -> {
                    unit.setName(TEST_UNITS_NAME + unit.getId());
                    unit.setItems(testItems);
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
