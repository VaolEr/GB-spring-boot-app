package com.example.storehouse.service;

import static com.example.storehouse.service.ServiceTestData.TEST_CATEGORY_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_CATEGORY_NAME;
import static com.example.storehouse.service.ServiceTestData.TEST_ITEMS_NAME;
import static com.example.storehouse.service.ServiceTestData.TEST_ITEMS_SKU;
import static com.example.storehouse.service.ServiceTestData.TEST_ITEM_1_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_ITEM_2_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_ITEM_3_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_SUPPLIER_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_SUPPLIER_NAME;
import static com.example.storehouse.util.SuppliersUtil.fromSupplierTo;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.repository.SuppliersRepository;
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

@SpringJUnitConfig(SuppliersService.class)
public class SuppliersServiceTests {

    @Autowired
    private SuppliersService service;

    @MockBean
    private SuppliersRepository suppliersRepository;

    Item testItemOne,
        testItemTwo,
        testItemThree;

    List<Item> testItems;

    Category testCategory;

    Supplier testSupplier;

    @BeforeEach
    void setUp() {
        testSupplier = new Supplier();
        testSupplier.setId(TEST_SUPPLIER_ID);
        testSupplier.setName(TEST_SUPPLIER_NAME);

        testCategory = new Category();
        testCategory.setId(TEST_CATEGORY_ID);
        testCategory.setName(TEST_CATEGORY_NAME);

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
        testSupplier.setItems(testItems);
    }

    @DisplayName("Should return suppliers where name contains TEST_SUPPLIER_NAME")
    @Test
    void getByNameAndNamePresent() {
        // Given
        List<Supplier> listSuppliers = List.of(testSupplier);
        when(suppliersRepository.findByNameContaining(testSupplier.getName())).thenReturn(listSuppliers);

        // When
        List<Supplier> returnedSuppliers = service.get(testSupplier.getName());

        // Then
        verify(suppliersRepository).findByNameContaining(testSupplier.getName());
        assertThat(returnedSuppliers).containsExactly(testSupplier);
    }

    @DisplayName("Should return empty content")
    @Test
    void getByNameAndNameAbsent() {
        // Given
        String supplierName = "name is absent";
        List<Supplier> listSuppliers = List.of(); // Collections.emptyList()
        when(suppliersRepository.findByNameContaining(supplierName)).thenReturn(listSuppliers);

        // When
        List<Supplier> returnedSuppliers = service.get(supplierName);

        // Then
        verify(suppliersRepository).findByNameContaining(supplierName);
        assertThat(returnedSuppliers).isEmpty();
    }

    @DisplayName("Should return one supplier by specified Supplier id")
    @Test
    void getByIdWithPresentId() {
        // Given
        when(suppliersRepository.findById(TEST_SUPPLIER_ID)).thenReturn(Optional.of(testSupplier));

        // When
        Supplier returnedSupplier = service.getById(TEST_SUPPLIER_ID);

        // Then
        verify(suppliersRepository).findById(TEST_SUPPLIER_ID);
        assertEquals(returnedSupplier, testSupplier);
    }

    @DisplayName("Have non-existent Supplier id, must thrown NotFoundException")
    @Test
    void getByIdWithAbsentId() {
        // Given
        when(suppliersRepository.findById(1)).thenReturn(Optional.empty());

        // When
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> service.getById(1));

        // Then
        verify(suppliersRepository).findById(1);
        assertEquals(notFoundException.getMessage(), "Not found entity with type is 'Supplier' and identifier is '1'");
    }

    @DisplayName("Should create new Supplier from SupplierTo and return it")
    @Test
    void createIsOk() {
        // Given
        SupplierTo newSupplierTo = SupplierTo.builder()
                                             .name("New category")
                                             .build();
        Supplier newSupplier = fromSupplierTo(newSupplierTo);
        when(suppliersRepository.save(newSupplier)).thenReturn(newSupplier);

        // When
        Supplier createdSupplier = service.create(newSupplierTo);

        // Then
        verify(suppliersRepository).save(newSupplier);
        assertEquals(createdSupplier, newSupplier);
    }

    @DisplayName("Should correctly update category where Supplier.id is equal to /path/{id}")
    @Test
    void updateIsOkAndItemIdIsConsistent() {
        // Given
        int supplierId = 411;
        int pathId = 411;
        SupplierTo updatedCategoryTo = SupplierTo.builder()
                                                 .id(supplierId)
                                                 .name("Updated category")
                                                 .build();
        Supplier updatedSupplier = fromSupplierTo(updatedCategoryTo);
        when(suppliersRepository.save(updatedSupplier)).thenReturn(updatedSupplier);

        // When
        Supplier savedSupplier = service.update(updatedCategoryTo, pathId);

        // Then
        verify(suppliersRepository).save(updatedSupplier);
        assertEquals(updatedSupplier, savedSupplier);
        assertEquals(savedSupplier.getId(), pathId);
    }

    @DisplayName("Have Supplier.id is not equal to /path/{id}, must thrown IllegalRequestDataException")
    @Test
    void updateIsFailedAndItemIdNotConsistent() {
        // Given
        int supplierId = 411;
        int pathId = 511;
        SupplierTo updatedSupplierTo = toSupplierTo(testSupplier);
        updatedSupplierTo.setId(supplierId);
        Supplier updatedSupplier = fromSupplierTo(updatedSupplierTo);
        when(suppliersRepository.save(updatedSupplier)).thenReturn(updatedSupplier);

        // When
        assertThatThrownBy(() -> service.update(updatedSupplierTo, pathId))

            // Then
            .isInstanceOfSatisfying(IllegalRequestDataException.class,
                                    e -> assertEquals(String.format("AbstractBaseEntity(id=%d) must be with id = %d", supplierId, pathId), e.getMessage())
            );
    }

    @Test
    void deleteIsOk() {
        // Given

        // When
        service.delete(TEST_CATEGORY_ID);

        // Then
        verify(suppliersRepository).deleteById(TEST_CATEGORY_ID);
    }
}
