package com.example.storehouse.web.items;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.ItemStorehouse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_ITEM_1_ID;
import static com.example.storehouse.util.ItemsUtil.toItemToWithBalance;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserItemsControllerTest extends AbstractItemsControllerTest {

    @BeforeEach
    void setUpUser() {
        when(jwtTokenProvider.getAuthentication(AUTH_TOKEN)).thenReturn(mockAuthorize(createTestUserUser()));
    }

    @Test
    @Override
    @DisplayName("create not permitted")
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
    @DisplayName("update not permitted")
    @SneakyThrows
    void update() {
        // Given
        testItemOne.setItemStorehouses(super.createItemStorehouses().toArray(new ItemStorehouse[2]));
        ItemTo createdItem = toItemToWithBalance(testItemOne);

        // When
        mvc
            .perform(put(itemsPath + "/{id}", TEST_ITEM_1_ID)
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
    @DisplayName("delete not permitted")
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
