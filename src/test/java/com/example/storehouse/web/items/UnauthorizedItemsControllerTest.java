package com.example.storehouse.web.items;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.ItemStorehouse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_ITEM_1_ID;
import static com.example.storehouse.util.ItemsUtil.toItemToWithBalance;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UnauthorizedItemsControllerTest extends AbstractItemsControllerTest {

    @BeforeEach
    void setUpUnauthorized() {
        when(jwtTokenProvider.validateToken(AUTH_TOKEN)).thenReturn(false);
    }

    @Test
    @Override
    @SneakyThrows
    void getByName() {
        // Given

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
    @Override
    @SneakyThrows
    void getAllUnpaged() {
        // Given

        // When
        mvc
            .perform(get(itemsPath)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(itemsService);
    }

    @Override
    void getAllPaged() {
        // do nothing
    }

    @Test
    @Override
    @SneakyThrows
    void getById() {
        // Given

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

    @Disabled
    @Override
    void getByIdNotFound() {
        // do nothing
    }

    @Test
    @Override
    @SneakyThrows
    void create() {
        // Given
        testItemOne.setItemStorehouses(super.createItemStorehouses().toArray(new ItemStorehouse[2]));
        ItemTo createdItem = toItemToWithBalance(testItemOne);

        // When
        mvc
            .perform(post(itemsPath)
                .headers(headers)
                .content(objectMapper.writeValueAsString(createdItem))
            )
            .andDo(print())

            // Then
            .andExpect(status().isForbidden())
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
        ItemTo updatedItem = toItemToWithBalance(testItemOne);

        // When
        mvc
            .perform(put(itemsPath + "/{id}", TEST_ITEM_1_ID)
                .headers(headers)
                .content(objectMapper.writeValueAsString(updatedItem))
            )
            .andDo(print())

            // Then
            .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(itemsService);
    }

    @Test
    @Override
    @SneakyThrows
    void delete() {
        // Given

        // When
        mvc
            .perform(MockMvcRequestBuilders.delete(itemsPath + "/{id}", TEST_ITEM_1_ID)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(itemsService);
    }

}
