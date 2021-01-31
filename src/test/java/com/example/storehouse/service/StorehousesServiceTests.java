package com.example.storehouse.service;

import static com.example.storehouse.TestData.TEST_CATEGORY_ID;
import static com.example.storehouse.TestData.TEST_CATEGORY_NAME;
import static com.example.storehouse.TestData.TEST_ITEMS_NAME;
import static com.example.storehouse.TestData.TEST_ITEMS_SKU;
import static com.example.storehouse.TestData.TEST_ITEM_1_ID;
import static com.example.storehouse.TestData.TEST_ITEM_2_ID;
import static com.example.storehouse.TestData.TEST_ITEM_3_ID;
import static com.example.storehouse.TestData.TEST_STOREHOUSE_1_ID;
import static com.example.storehouse.TestData.TEST_STOREHOUSE_1_NAME;
import static com.example.storehouse.TestData.TEST_SUPPLIER_ID;
import static com.example.storehouse.TestData.TEST_SUPPLIER_NAME;
import static com.example.storehouse.util.StorehousesUtil.fromStorehouseTo;
import static com.example.storehouse.util.StorehousesUtil.toStorehouseTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.storehouse.dto.StorehouseTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.repository.ItemsRepository;
import com.example.storehouse.repository.StorehousesRepository;
import com.example.storehouse.util.exception.IllegalRequestDataException;
import com.example.storehouse.util.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(StorehousesService.class)
@MockBean(ItemsRepository.class)
public class StorehousesServiceTests {

    @Autowired
    private StorehousesService service;

    @MockBean
    private StorehousesRepository storehousesRepository;

    Item testItemOne,
        testItemTwo,
        testItemThree;

    List<Item> testItems;

    Category testCategory;

    Supplier testSupplier;

    Storehouse testStorehouse;

    @BeforeEach
    void setUp() {
        testStorehouse = new Storehouse();
        testStorehouse.setId(TEST_STOREHOUSE_1_ID);
        testStorehouse.setName(TEST_STOREHOUSE_1_NAME);

        testCategory = new Category();
        testCategory.setId(TEST_CATEGORY_ID);
        testCategory.setName(TEST_CATEGORY_NAME);

        testSupplier = new Supplier();
        testSupplier.setId(TEST_SUPPLIER_ID);
        testSupplier.setName(TEST_SUPPLIER_NAME);

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
                        })
                        .collect(Collectors.toList());
        testCategory.setItems(testItems);
    }

    @DisplayName("Should return storehouses where name contains TEST_STOREHOUSE_1_NAME")
    @Test
    void getByNameAndNamePresent() {
        // Given
        List<Storehouse> listStorehouses = List.of(testStorehouse);
        when(storehousesRepository
                 .findByNameContaining(testStorehouse.getName())).thenReturn(listStorehouses);

        // When
        List<Storehouse> returnedStorehouses = service.get(testStorehouse.getName());

        // Then
        verify(storehousesRepository).findByNameContaining(testStorehouse.getName());
        assertThat(returnedStorehouses).containsExactly(testStorehouse);
    }

    @DisplayName("Should return empty content")
    @Test
    void getByNameAndNameAbsent() {
        // Given
        String storehouseName = "name is absent";
        List<Storehouse> listStorehouses = List.of(); // Collections.emptyList()
        when(storehousesRepository.findByNameContaining(storehouseName)).thenReturn(listStorehouses);

        // When
        List<Storehouse> returnedStorehouses = service.get(storehouseName);

        // Then
        verify(storehousesRepository).findByNameContaining(storehouseName);
        assertThat(returnedStorehouses).isEmpty();
    }

    @DisplayName("Should return one storehouse by specified Storehouse id")
    @Test
    void getByIdWithPresentId() {
        // Given
        when(storehousesRepository.findById(TEST_STOREHOUSE_1_ID)).thenReturn(Optional.of(testStorehouse));

        // When
        Storehouse returnedStorehouse = service.getById(TEST_STOREHOUSE_1_ID);

        // Then
        verify(storehousesRepository).findById(TEST_STOREHOUSE_1_ID);
        assertEquals(returnedStorehouse, testStorehouse);
    }

    @DisplayName("Have non-existent Storehouse id, must thrown NotFoundException")
    @Test
    void getByIdWithAbsentId() {
        // Given
        when(storehousesRepository.findById(1)).thenReturn(Optional.empty());

        // When
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> service.getById(1));

        // Then
        verify(storehousesRepository).findById(1);
        assertEquals(notFoundException.getMessage(), "Not found entity with type is 'Storehouse' and identifier is '1'");
    }

    @DisplayName("Should create new Storehouse from StorehouseTo and return it")
    @Test
    void createIsOk() {
        // Given
        StorehouseTo newStorehouseTo = StorehouseTo.builder()
                                               .name("New storehouse")
                                               .build();
        Storehouse newStorehouse = fromStorehouseTo(newStorehouseTo);
        when(storehousesRepository.save(newStorehouse)).thenReturn(newStorehouse);

        // When
        Storehouse createdStorehouse = service.create(newStorehouseTo);

        // Then
        verify(storehousesRepository).save(newStorehouse);
        assertEquals(createdStorehouse, newStorehouse);
    }

    @DisplayName("Should correctly update storehouse where Storehouse.id is equal to /path/{id}")
    @Test
    void updateIsOkAndItemIdIsConsistent() {
        // Given
        int storehouseId = 411;
        int pathId = 411;
        StorehouseTo updatedStorehouseTo = StorehouseTo.builder()
                                                 .id(storehouseId)
                                                 .name("Updated category")
                                                 .build();
        Storehouse updatedStorehouse = fromStorehouseTo(updatedStorehouseTo);
        when(storehousesRepository.save(updatedStorehouse)).thenReturn(updatedStorehouse);

        // When
        Storehouse savedStorehouse = service.update(updatedStorehouseTo, pathId);

        // Then
        verify(storehousesRepository).save(updatedStorehouse);
        assertEquals(updatedStorehouse, savedStorehouse);
        assertEquals(savedStorehouse.getId(), pathId);
    }

    @DisplayName("Have Storehouse.id is not equal to /path/{id}, must thrown IllegalRequestDataException")
    @Test
    void updateIsFailedAndItemIdNotConsistent() {
        // Given
        int storehouseId = 411;
        int pathId = 511;
        StorehouseTo updatedStorehouseTo = toStorehouseTo(testStorehouse);
        updatedStorehouseTo.setId(storehouseId);
        Storehouse updatedStorehouse = fromStorehouseTo(updatedStorehouseTo);
        when(storehousesRepository.save(updatedStorehouse)).thenReturn(updatedStorehouse);

        // When
        assertThatThrownBy(() -> service.update(updatedStorehouseTo, pathId))

            // Then
            .isInstanceOfSatisfying(IllegalRequestDataException.class,
                                    e -> assertEquals(String.format("AbstractBaseEntity(id=%d) must be with id = %d", storehouseId, pathId), e.getMessage())
            );
    }

    @Test
    void deleteIsOk() {
        // Given

        // When
        service.delete(TEST_STOREHOUSE_1_ID);

        // Then
        verify(storehousesRepository).deleteById(TEST_STOREHOUSE_1_ID);
    }
}
