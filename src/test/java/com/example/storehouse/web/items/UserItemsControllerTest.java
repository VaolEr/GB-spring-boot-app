package com.example.storehouse.web.items;

import static com.example.storehouse.TestData.TEST_ITEM_1_ID;
import static com.example.storehouse.TestData.TEST_USER_EMAIL;
import static com.example.storehouse.TestData.TEST_USER_FIRST_NAME;
import static com.example.storehouse.TestData.TEST_USER_ID;
import static com.example.storehouse.TestData.TEST_USER_LAST_NAME;
import static com.example.storehouse.TestData.TEST_USER_PASSWORD;
import static com.example.storehouse.TestData.TEST_USER_ROLE;
import static com.example.storehouse.TestData.TEST_USER_STATUS;
import static com.example.storehouse.util.ItemsUtil.toItemToWithBalance;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserItemsControllerTest extends AbstractItemsControllerTest {

    @BeforeEach
    void setUpUser() {
        when(jwtTokenProvider.getAuthentication(AUTH_TOKEN)).thenReturn(super.mockAuthorize(createTestUserUser()));
    }

    @Test
    @SneakyThrows
    void createNotPermitted() {
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
    @SneakyThrows
    void updateNotPermitted() {
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
    @SneakyThrows
    void deleteNotPermitted() {
        // Given

        // When
        mvc
            .perform(delete(itemsPath + "/{id}", TEST_ITEM_1_ID)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(itemsService);
    }

    private User createTestUserUser() {
        User user = new User();
        user.setId(TEST_USER_ID);
        user.setEmail(TEST_USER_EMAIL);
        user.setPassword(TEST_USER_PASSWORD);
        user.setFirstName(TEST_USER_FIRST_NAME);
        user.setLastName(TEST_USER_LAST_NAME);
        user.setRole(TEST_USER_ROLE);
        user.setStatus(TEST_USER_STATUS);
        return user;
    }

}
