package com.example.storehouse.web.categories;

import com.example.storehouse.dto.CategoryTo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_CATEGORY_1_ID;
import static com.example.storehouse.util.CategoriesUtil.toCategoryTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UnauthorizedCategoriesControllerTests extends AbstractCategoriesControllerTest {

    @BeforeEach
    void setUpUnauthorized() {
        when(jwtTokenProvider.validateToken(AUTH_TOKEN)).thenReturn(false);
    }

    @Test
    //@Override
    @SneakyThrows
    void getByName() {
        // Given

        // When
        mvc
                .perform(get(categoriesPath)
                        .headers(headers)
                        .param("name", testCategoryOne.getName())
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
    @SneakyThrows
    void getAll() {
        // Given

        // When
        mvc
                .perform(get(categoriesPath)
                        .headers(headers)
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
    @SneakyThrows
    void getById() {
        // Given

        // When
        mvc
                .perform(get(categoriesPath + "/{id}", TEST_CATEGORY_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(categoriesService);
    }

    @Override
    void getByIdNotFound() {
        // do nothing
    }

    @Test
    @Override
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
    @SneakyThrows
    void update() {
        // Given
        CategoryTo updatedCategory = toCategoryTo(testCategoryOne);

        // When
        mvc
                .perform(put(categoriesPath + "/{id}", TEST_CATEGORY_1_ID)
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

}
