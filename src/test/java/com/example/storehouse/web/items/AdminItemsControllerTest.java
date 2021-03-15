package com.example.storehouse.web.items;

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
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.ItemStorehouse;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class AdminItemsControllerTest extends AbstractItemsControllerTest {

    @BeforeEach
    void setUpAdmin() {
        when(jwtTokenProvider.getAuthentication(AUTH_TOKEN)).thenReturn(mockAuthorize(createTestUserAdmin()));
    }

    @Test
    @Override
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
            .andExpect(header().exists(LOCATION))
            // TODO как бы тут достать URL? Это будет работать в таком виде?
            .andExpect(header().string(LOCATION, "http://localhost" + itemsPath + TEST_ITEM_1_ID))
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
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
    @Override
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
            .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(itemsService).update(updatedItem, TEST_ITEM_1_ID);
    }

    @Disabled
    @Test
    @SneakyThrows
    void updateInvalid() {
        // Invalid ItemTo -> BadRequest(400)
    }

    @Test
    @Override
    @SneakyThrows
    void delete() {
        // Given
        doNothing().when(itemsService).delete(TEST_ITEM_1_ID);

        // When
        mvc
            .perform(MockMvcRequestBuilders.delete(itemsPath + "/{id}", TEST_ITEM_1_ID)
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
            .perform(MockMvcRequestBuilders.delete(itemsPath + "/{id}", absentedItemId)
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

}
