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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
import com.example.storehouse.model.User;
import com.example.storehouse.security.JwtTokenProvider;
import com.example.storehouse.service.ItemsService;
import com.example.storehouse.util.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

//@WebMvcTest(ItemsControllerTest.class)
// Я тут с ходу не разобрался, как замокать всю кучу security-зависимостей
// для загрузки только требуемого контекста, поэтому поставил пока загрузку всего
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractItemsControllerTest {

    @Value("${app.endpoints.base_path}" + "${app.endpoints.items.base_url}/")
    String itemsPath;

    @Value("${app.jwt.header}")
    String authHeader;

    @Autowired
    MockMvc mvc;

    @MockBean
    ItemsService itemsService;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    static final String AUTH_TOKEN = "jwt-auth-token";

    HttpHeaders headers;
    Pageable unpaged;
    ObjectMapper objectMapper;
    Item testItemOne,
        testItemTwo,
        testItemThree;
    List<Item> testItems;
    Category testCategory;
    Supplier testSupplier;
    Unit testUnit;
    Storehouse testStorehouseOne,
        testStorehouseTwo;

    @PostConstruct
    void prepare() {
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);

        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(headers.getFirst(authHeader));
        when(jwtTokenProvider.validateToken(AUTH_TOKEN)).thenReturn(true);

        createTestEntities();

        // Results be returned with default Pageable settings
        unpaged = Pageable.unpaged();
    }

    @Test
    void contextLoads() {
        assertNotNull(mvc);
        assertNotNull(itemsService);
        assertNotNull(jwtTokenProvider);
    }

    @Test
    @SneakyThrows
    void getUnauthorizedAll() {
        // Given
        when(jwtTokenProvider.validateToken(AUTH_TOKEN)).thenReturn(false);

        // When
        mvc
            .perform(get(itemsPath)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isForbidden())
        ;
        // посмотреть, почему 2 вызова и можно ли сделать 1
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(itemsService);
    }

    @Test
    @SneakyThrows
    void getUnauthorizedByName() {
        // Given
        when(jwtTokenProvider.validateToken(AUTH_TOKEN)).thenReturn(false);

        // When
        mvc
            .perform(get(itemsPath)
                .headers(headers)
                .param("name", testItemOne.getName())
            )
            .andDo(print())

            // Then
            .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(itemsService);
    }

    @Test
    @SneakyThrows
    void getUnauthorizedById() {
        // Given
        when(jwtTokenProvider.validateToken(AUTH_TOKEN)).thenReturn(false);

        // When
        mvc
            .perform(get(itemsPath + "/{id}", TEST_ITEM_1_ID)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(itemsService);
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
    void getAllPaged() {
    }

    @Disabled
    @Test
    void getByNameUnpaged() {
    }

    @Disabled
    @Test
    void getByNamePaged() {
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

    Authentication mockAuthorize(User authorizeAs) {
        return new UsernamePasswordAuthenticationToken(
            new org.springframework.security.core.userdetails.User(
                authorizeAs.getEmail(),
                authorizeAs.getPassword(),
                true,
                true,
                true,
                true,
                authorizeAs.getRole().getAuthorities()
            ),
            null, authorizeAs.getRole().getAuthorities()
        );
    }

}
