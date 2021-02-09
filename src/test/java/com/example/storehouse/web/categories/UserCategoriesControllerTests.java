package com.example.storehouse.web.categories;

import com.example.storehouse.dto.CategoryTo;
import com.example.storehouse.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.*;
import static com.example.storehouse.util.CategoriesUtil.toCategoryTo;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserCategoriesControllerTests extends AbstractCategoriesControllerTest {

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
        CategoryTo createdCategory = toCategoryTo(testCategoryOne);

        // When
        mvc
                .perform(post(categoriesPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdCategory))
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(categoriesService);
    }

    @Test
    @Override
    @DisplayName("update not permitted")
    @SneakyThrows
    void update() {
        // Given
        CategoryTo updatedCategory = toCategoryTo(testCategoryOne);

        // When
        mvc
                .perform(put(categoriesPath + "/{id}", TEST_ITEM_1_ID)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(updatedCategory))
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(categoriesService);
    }

    //TODO решить как будет реализовано удаление данных при связанных талицах.
    // А пока не решили - Disabled.
    @Disabled
    @Test
    @Override
    @DisplayName("delete not permitted")
    @SneakyThrows
    void delete() {
        // Given

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(categoriesPath + "/{id}", TEST_CATEGORY_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(categoriesService);
    }

    //TODO вынести в файл TestData?
    // Так как используется в нескольких тестовых классах.
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
