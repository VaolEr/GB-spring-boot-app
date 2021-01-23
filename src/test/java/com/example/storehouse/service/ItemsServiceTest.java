package com.example.storehouse.service;

import static com.example.storehouse.service.ServiceTestData.TEST_CATEGORY_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_CATEGORY_NAME;
import static com.example.storehouse.service.ServiceTestData.TEST_ITEMS_NAME;
import static com.example.storehouse.service.ServiceTestData.TEST_ITEMS_SKU;
import static com.example.storehouse.service.ServiceTestData.TEST_ITEM_1_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_ITEM_2_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_ITEM_3_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_STOREHOUSE_1_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_STOREHOUSE_1_NAME;
import static com.example.storehouse.service.ServiceTestData.TEST_STOREHOUSE_2_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_STOREHOUSE_2_NAME;
import static com.example.storehouse.service.ServiceTestData.TEST_SUPPLIER_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_SUPPLIER_NAME;
import static com.example.storehouse.service.ServiceTestData.TEST_UNIT_ID;
import static com.example.storehouse.util.CategoriesUtil.toCategoryTos;
import static com.example.storehouse.util.ItemsUtil.fromItemTo;
import static com.example.storehouse.util.ItemsUtil.toItemTo;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTo;
import static com.example.storehouse.util.UnitsUtil.toUnitTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.storehouse.dto.ItemStorehouseTo;
import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.model.Unit;
import com.example.storehouse.repository.CategoriesRepository;
import com.example.storehouse.repository.ItemsRepository;
import com.example.storehouse.repository.StorehousesRepository;
import com.example.storehouse.repository.SuppliersRepository;
import com.example.storehouse.repository.UnitsRepository;
import com.example.storehouse.util.ItemStorehousesUtil;
import com.example.storehouse.util.ItemsUtil;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(ItemsService.class)
class ItemsServiceTest {

    @Autowired
    private ItemsService service;

    @MockBean
    private ItemsRepository itemsRepository;

    @MockBean
    private SuppliersRepository suppliersRepository;

    @MockBean
    private CategoriesRepository categoriesRepository;

    @MockBean
    private StorehousesRepository storehousesRepository;

    @MockBean
    private UnitsRepository unitsRepository;

    Item testItemOne,
        testItemTwo,
        testItemThree;
    List<Item> testItems;
    Category testCategory;
    Supplier testSupplier;
    Unit testUnit;
    Storehouse testStorehouseOne,
        testStorehouseTwo;
    Pageable pageableUnpaged;

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setId(TEST_CATEGORY_ID);
        testCategory.setName(TEST_CATEGORY_NAME);

        testSupplier = new Supplier();
        testSupplier.setId(TEST_SUPPLIER_ID);
        testSupplier.setName(TEST_SUPPLIER_NAME);

        testUnit = new Unit();

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
        // testItems = List.of(testItemOne, testItemTwo, testItemThree);
        // testItems.forEach(item -> {
        // item processing (set id, name, etc)
        // });

        pageableUnpaged = Pageable.unpaged();

        when(suppliersRepository.findById(TEST_SUPPLIER_ID)).thenReturn(Optional.of(testSupplier));
        when(categoriesRepository.findById(TEST_CATEGORY_ID)).thenReturn(Optional.of(testCategory));
        when(unitsRepository.findById(TEST_UNIT_ID)).thenReturn(Optional.of(testUnit));
        // TODO: refactor service Create/Update and use Iterator Mock?
        when(storehousesRepository.findById(TEST_STOREHOUSE_1_ID)).thenReturn(Optional.of(testStorehouseOne));
        when(storehousesRepository.findById(TEST_STOREHOUSE_2_ID)).thenReturn(Optional.of(testStorehouseTwo));
    }

    @DisplayName("Should return all items in one page")
    @Test
    void getAllUnpaged() {
        // Given
        Page<Item> pagedItems = new PageImpl<>(testItems, pageableUnpaged, testItems.size());
        when(itemsRepository.findAll(isA(Pageable.class))).thenReturn(pagedItems);

        // When
        Page<ItemTo> returnedItems = service.get(pageableUnpaged, null);

        // Then
        verify(itemsRepository).findAll(pageableUnpaged);
        assertThat(returnedItems.getContent().stream().map(ItemsUtil::fromItemTo))
            .containsExactly(testItems.toArray(Item[]::new));
    }

    @DisplayName("Should return two items in one page")
    @Test
    void getAllPaged() {
        // Given
        Pageable pageable = PageRequest.of(0, 2);
        Page<Item> pagedItems = new PageImpl<>(List.of(testItemOne, testItemTwo), pageable, 2L);
        when(itemsRepository.findAll(isA(Pageable.class))).thenReturn(pagedItems);

        // When
        Page<ItemTo> returnedItems = service.get(pageable, null);

        // Then
        verify(itemsRepository).findAll(pageable);
        assertThat(returnedItems.getContent().stream().map(ItemsUtil::fromItemTo)).containsExactly(testItemOne, testItemTwo);
    }

    @DisplayName("Should return items where name contains TEST_ITEM_3_ID.value")
    @Test
    void getByNameAndNamePresent() {
        // Given
        Page<Item> pagedItems = new PageImpl<>(List.of(testItemThree)); // default Pageable is Pageable.unpaged()
        when(itemsRepository.findByNameContaining(testItemThree.getName(), pageableUnpaged)).thenReturn(pagedItems);

        // When
        Page<ItemTo> returnedItems = service.get(pageableUnpaged, testItemThree.getName());

        // Then
        verify(itemsRepository).findByNameContaining(testItemThree.getName(), pageableUnpaged);
        assertThat(returnedItems.getContent().stream().map(ItemsUtil::fromItemTo)).containsExactly(testItemThree);
    }

    @DisplayName("Should return empty content")
    @Test
    void getByNameAndNameAbsent() {
        // Given
        String itemName = "name is absent";
        Page<Item> pagedItems = new PageImpl<>(List.of()); // Collections.emptyList()
        when(itemsRepository.findByNameContaining(itemName, pageableUnpaged)).thenReturn(pagedItems);

        // When
        Page<ItemTo> returnedItems = service.get(pageableUnpaged, itemName);

        // Then
        verify(itemsRepository).findByNameContaining(itemName, pageableUnpaged);
        assertThat(returnedItems.getContent()).isEmpty();
    }

    @DisplayName("Should return one item by specified Item id")
    @Test
    void getByIdWithPresentId() {
        // Given
        when(itemsRepository.findById(TEST_ITEM_1_ID)).thenReturn(Optional.of(testItemOne));

        // When
        Item returnedItem = service.getById(TEST_ITEM_1_ID);

        // Then
        verify(itemsRepository).findById(TEST_ITEM_1_ID);
        assertEquals(returnedItem, testItemOne);
    }

    @DisplayName("Have non-existent Item id, must thrown NotFoundException")
    @Test
    void getByIdWithAbsentId() {
        // Given
        when(itemsRepository.findById(1)).thenReturn(Optional.empty());

        // When
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> service.getById(1));

        // Then
        verify(itemsRepository).findById(1);
        assertEquals(notFoundException.getMessage(), "Not found entity with type is 'Item' and identifier is '1'");
    }

    @DisplayName("Should create new Item from ItemTo and return it")
    @Test
    void createIsOk() {
        // Given
        ItemTo newItemTo = ItemTo.builder()
            .name("New item")
            .sku("#new-item-sku")
            .unit(toUnitTo(testUnit))
            .categories(toCategoryTos(List.of(testCategory)))
            .supplier(toSupplierTo(testSupplier))
            .build();
        newItemTo.setItemsStorehouses(createItemStorehouseTos());
        Item newItem = fromItemTo(newItemTo);
        when(itemsRepository.save(newItem)).thenReturn(newItem);

        // When
        Item createdItem = service.create(newItemTo);

        // Then
        verify(itemsRepository).save(newItem);
        assertEquals(createdItem, newItem);
    }

    @DisplayName("Create item with non-existent Supplier, must thrown NotFoundException")
    @Test
    void createIsFailIfSupplierAbsent() {
        // Given
        ItemTo newItemTo = toItemTo(testItemOne);
        when(suppliersRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When
        assertThatThrownBy(() -> service.create(newItemTo))

            // Then
            .isInstanceOfSatisfying(NotFoundException.class, e -> assertEquals(
                String.format("Not found entity with type is '%s' and identifier is '%d'", testSupplier.getClass().getSimpleName(),
                    testSupplier.getId()), e.getMessage())
            );
    }

    @DisplayName("Should correctly update item where Item.id is equal to /path/{id}")
    @Test
    void updateIsOkAndItemIdIsConsistent() {
        // Given
        int itemId = 411;
        int pathId = 411;
        ItemTo updatedItemTo = ItemTo.builder()
            .id(itemId)
            .name("Updated item")
            .sku("#updated-item-sku")
            .unit(toUnitTo(testUnit))
            .categories(toCategoryTos(List.of(testCategory)))
            .supplier(toSupplierTo(testSupplier))
            .build();
        updatedItemTo.setItemsStorehouses(createItemStorehouseTos());
        Item updatedItem = fromItemTo(updatedItemTo);
        when(itemsRepository.save(updatedItem)).thenReturn(updatedItem);

        // When
        Item savedItem = service.update(updatedItemTo, pathId);

        // Then
        verify(itemsRepository).save(updatedItem);
        assertEquals(updatedItem, savedItem);
        assertEquals(savedItem.getId(), pathId);
    }

    @DisplayName("Have Item.id is not equal to /path/{id}, must thrown IllegalRequestDataException")
    @Test
    void updateIsFailedAndItemIdNotConsistent() {
        // Given
        int itemId = 411;
        int pathId = 511;
        ItemTo updatedItemTo = toItemTo(testItemOne);
        updatedItemTo.setId(itemId);
        Item updatedItem = fromItemTo(updatedItemTo);
        when(itemsRepository.save(updatedItem)).thenReturn(updatedItem);

        // When
        assertThatThrownBy(() -> service.update(updatedItemTo, pathId))

            // Then
            .isInstanceOfSatisfying(IllegalRequestDataException.class,
                e -> assertEquals(String.format("AbstractBaseEntity(id=%d) must be with id = %d", itemId, pathId), e.getMessage())
            );
    }

    @Test
    void deleteIsOk() {
        // Given

        // When
        service.delete(TEST_ITEM_1_ID);

        // Then
        verify(itemsRepository).deleteById(TEST_ITEM_1_ID);
    }

    private List<ItemStorehouseTo> createItemStorehouseTos() {
        ItemStorehouse testItemStorehouseOne = new ItemStorehouse();
        testItemStorehouseOne.setStorehouse(testStorehouseOne);
        testItemStorehouseOne.setQuantity(14);

        ItemStorehouse testItemStorehouseTwo = new ItemStorehouse();
        testItemStorehouseTwo.setStorehouse(testStorehouseTwo);
        testItemStorehouseTwo.setQuantity(18);

        return List.of(testItemStorehouseOne, testItemStorehouseTwo).stream()
            .map(ItemStorehousesUtil::toItemStorehouseTo)
            .collect(Collectors.toList());
    }

}