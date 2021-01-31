package com.example.storehouse.web.items;

import static com.example.storehouse.TestData.TEST_ADMIN_EMAIL;
import static com.example.storehouse.TestData.TEST_ADMIN_FIRST_NAME;
import static com.example.storehouse.TestData.TEST_ADMIN_ID;
import static com.example.storehouse.TestData.TEST_ADMIN_LAST_NAME;
import static com.example.storehouse.TestData.TEST_ADMIN_PASSWORD;
import static com.example.storehouse.TestData.TEST_ADMIN_ROLE;
import static com.example.storehouse.TestData.TEST_ADMIN_STATUS;
import static com.example.storehouse.TestData.TEST_ITEM_1_ID;
import static com.example.storehouse.util.ItemsUtil.toItemToWithBalance;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.User;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;

class AdminItemsControllerTest extends AbstractItemsControllerTest {

    @BeforeEach
    void setUpAdmin() {
        when(jwtTokenProvider.getAuthentication(AUTH_TOKEN)).thenReturn(super.mockAuthorize(createTestUserAdmin()));
    }

    @Test
    @SneakyThrows
    void create() {
        // Given
        testItemOne.setItemStorehouses(super.createItemStorehouses().toArray(new ItemStorehouse[2]));
        ItemTo createdItem = toItemToWithBalance(testItemOne);
        when(itemsService.create(isA(ItemTo.class))).thenReturn(testItemOne);

        // When
        mvc
            .perform(post(itemsPath)
                .headers(headers)
                .content(objectMapper.writeValueAsString(createdItem))
            )
            .andDo(print())

            // Then
            .andExpect(status().isCreated())
            .andExpect(header().exists(HttpHeaders.LOCATION))
            // TODO как бы тут достать URL? Это будет работать в таком виде?
            .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost" + itemsPath + TEST_ITEM_1_ID))
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
            // TODO поправить проверку содержимого
            .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(itemsService).create(createdItem);
    }

    @Test
    @SneakyThrows
    void createInvalid() {
        // Given
        testItemOne.setItemStorehouses(super.createItemStorehouses().toArray(new ItemStorehouse[2]));
        ItemTo createdItem = toItemToWithBalance(testItemOne);
        createdItem.setItemsStorehouses(List.of());
        createdItem.setUnit(null);

        // When
        mvc
            .perform(post(itemsPath)
                .headers(headers)
                .content(objectMapper.writeValueAsString(createdItem))
            )
            .andDo(print())

            // Then
            .andExpect(status().isBadRequest())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(itemsService);
    }

    @Test
    @SneakyThrows
    void update() {
        // Given
        testItemOne.setItemStorehouses(super.createItemStorehouses().toArray(new ItemStorehouse[2]));
        testItemOne.setId(999);
        testItemOne.setName("updated item");
        ItemTo updatedItem = toItemToWithBalance(testItemOne);
        when(itemsService.update(isA(ItemTo.class), eq(TEST_ITEM_1_ID))).thenReturn(testItemOne);

        // When
        mvc
            .perform(put(itemsPath + "/{id}", TEST_ITEM_1_ID)
                .headers(headers)
                .content(objectMapper.writeValueAsString(updatedItem))
            )
            .andDo(print())

            // Then
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
            // TODO поправить проверку содержимого
            .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(itemsService).update(updatedItem, TEST_ITEM_1_ID);
    }

    @Test
    @SneakyThrows
    void deleteExisted() {
        // Given
        doNothing().when(itemsService).delete(TEST_ITEM_1_ID);

        // When
        mvc
            .perform(delete(itemsPath + "/{id}", TEST_ITEM_1_ID)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isNoContent())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(itemsService).delete(TEST_ITEM_1_ID);
    }

    @Test
    @SneakyThrows
    void deleteNotFound() {
        // Given
        int absentedItemId = 0;
        doThrow(EmptyResultDataAccessException.class).when(itemsService).delete(absentedItemId);

        // When
        mvc
            .perform(delete(itemsPath + "/{id}", absentedItemId)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(itemsService).delete(absentedItemId);
    }

    private User createTestUserAdmin() {
        User user = new User();
        user.setId(TEST_ADMIN_ID);
        user.setEmail(TEST_ADMIN_EMAIL);
        user.setPassword(TEST_ADMIN_PASSWORD);
        user.setFirstName(TEST_ADMIN_FIRST_NAME);
        user.setLastName(TEST_ADMIN_LAST_NAME);
        user.setRole(TEST_ADMIN_ROLE);
        user.setStatus(TEST_ADMIN_STATUS);
        return user;
    }

}
