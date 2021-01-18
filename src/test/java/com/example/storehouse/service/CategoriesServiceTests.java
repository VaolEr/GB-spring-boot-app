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
import static com.example.storehouse.util.CategoriesUtil.toCategoryTo;
import static com.example.storehouse.util.CategoriesUtil.fromCategoryTo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.storehouse.dto.CategoryTo;

import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.repository.CategoriesRepository;
import com.example.storehouse.util.exception.IllegalRequestDataException;
import com.example.storehouse.util.exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(CategoriesService.class)
public class CategoriesServiceTests {

    @Autowired
    private CategoriesService service;

    @MockBean
    private CategoriesRepository categoriesRepository;

    Item testItemOne,
         testItemTwo,
         testItemThree;

    List<Item> testItems;

    Category testCategory;

    Supplier testSupplier;

    @BeforeEach
    void setUp() {
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

    @DisplayName("Should return categories where name contains TEST_CATEGORY_NAME")
    @Test
    void getByNameAndNamePresent() {
        // Given
        List<Category> listCategories = List.of(testCategory);
        when(categoriesRepository.findByNameContaining(testCategory.getName())).thenReturn(listCategories);

        // When
        List<Category> returnedCategories = service.get(testCategory.getName());

        // Then
        verify(categoriesRepository).findByNameContaining(testCategory.getName());
        assertThat(returnedCategories).containsExactly(testCategory);
    }

    @DisplayName("Should return empty content")
    @Test
    void getByNameAndNameAbsent() {
        // Given
        String categoryName = "name is absent";
        List<Category> listCategories = List.of(); // Collections.emptyList()
        when(categoriesRepository.findByNameContaining(categoryName)).thenReturn(listCategories);

        // When
        List<Category> returnedCategories = service.get(categoryName);

        // Then
        verify(categoriesRepository).findByNameContaining(categoryName);
        assertThat(returnedCategories).isEmpty();
    }

    @DisplayName("Should return one category by specified Category id")
    @Test
    void getByIdWithPresentId() {
        // Given
        when(categoriesRepository.findById(TEST_CATEGORY_ID)).thenReturn(Optional.of(testCategory));

        // When
        Category returnedCategory = service.getById(TEST_CATEGORY_ID);

        // Then
        verify(categoriesRepository).findById(TEST_CATEGORY_ID);
        assertEquals(returnedCategory, testCategory);
    }

    @DisplayName("Have non-existent Category id, must thrown NotFoundException")
    @Test
    void getByIdWithAbsentId() {
        // Given
        when(categoriesRepository.findById(1)).thenReturn(Optional.empty());

        // When
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> service.getById(1));

        // Then
        verify(categoriesRepository).findById(1);
        assertEquals(notFoundException.getMessage(), "Not found entity with type is 'Category' and identifier is '1'");
    }

    @DisplayName("Should create new Category from CategoryTo and return it")
    @Test
    void createIsOk() {
        // Given
        CategoryTo newCategoryTo = CategoryTo.builder()
                                             .name("New category")
                                             .build();
        Category newCategory = fromCategoryTo(newCategoryTo);
        when(categoriesRepository.save(newCategory)).thenReturn(newCategory);

        // When
        Category createdCategory = service.create(newCategoryTo);

        // Then
        verify(categoriesRepository).save(newCategory);
        assertEquals(createdCategory, newCategory);
    }

    @DisplayName("Should correctly update category where Category.id is equal to /path/{id}")
    @Test
    void updateIsOkAndItemIdIsConsistent() {
        // Given
        int categoryId = 411;
        int pathId = 411;
        CategoryTo updatedCategoryTo = CategoryTo.builder()
                                     .id(categoryId)
                                     .name("Updated category")
                                     .build();
        Category updatedCategory = fromCategoryTo(updatedCategoryTo);
        when(categoriesRepository.save(updatedCategory)).thenReturn(updatedCategory);

        // When
        Category savedCategory = service.update(updatedCategoryTo, pathId);

        // Then
        verify(categoriesRepository).save(updatedCategory);
        assertEquals(updatedCategory, savedCategory);
        assertEquals(savedCategory.getId(), pathId);
    }

    @DisplayName("Have Category.id is not equal to /path/{id}, must thrown IllegalRequestDataException")
    @Test
    void updateIsFailedAndItemIdNotConsistent() {
        // Given
        int categoryId = 411;
        int pathId = 511;
        CategoryTo updatedCategoryTo = toCategoryTo(testCategory);
        updatedCategoryTo.setId(categoryId);
        Category updatedCategory = fromCategoryTo(updatedCategoryTo);
        when(categoriesRepository.save(updatedCategory)).thenReturn(updatedCategory);

        // When
        assertThatThrownBy(() -> service.update(updatedCategoryTo, pathId))

            // Then
            .isInstanceOfSatisfying(IllegalRequestDataException.class,
                                    e -> assertEquals(String.format("AbstractBaseEntity(id=%d) must be with id = %d", categoryId, pathId), e.getMessage())
            );
    }

    @Test
    void deleteIsOk() {
        // Given

        // When
        service.delete(TEST_CATEGORY_ID);

        // Then
        verify(categoriesRepository).deleteById(TEST_CATEGORY_ID);
    }
}
