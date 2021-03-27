package com.example.storehouse.web.categories;

import com.example.storehouse.dto.CategoryTo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_CATEGORY_1_ID;
import static com.example.storehouse.util.CategoriesUtil.toCategoryTo;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
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

class AdminCategoriesControllerTests extends AbstractCategoriesControllerTest {

    @BeforeEach
    void setUpAdmin() {
        when(jwtTokenProvider.getAuthentication(AUTH_TOKEN)).thenReturn(mockAuthorize(createTestUserAdmin()));
    }

    @Test
    @Override
    @SneakyThrows
    void create() {
        // Given
        CategoryTo createdCategory = toCategoryTo(testCategoryOne);
        when(categoriesService.create(isA(CategoryTo.class))).thenReturn(testCategoryOne);

        // When
        mvc
            .perform(post(categoriesPath)
                .headers(headers)
                .content(objectMapper.writeValueAsString(createdCategory))
            )
            .andDo(print())

            // Then
            .andExpect(status().isCreated())
            .andExpect(header().exists(LOCATION))
            .andExpect(header().string(LOCATION, "http://localhost" + categoriesPath + TEST_CATEGORY_1_ID))
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(categoriesService).create(createdCategory);
    }

    @Test
    @SneakyThrows
    void createInvalid() {
        // Given
        CategoryTo createdCategory = toCategoryTo(testCategoryOne);
        createdCategory.setName(null);
        // When
        mvc
                .perform(post(categoriesPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdCategory))
                )
                .andDo(print())

                // Then
                .andExpect(status().isBadRequest())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(categoriesService);
    }

    @Test
    @Override
    @SneakyThrows
    void update() {
        // Given
        testCategoryOne.setId(999);
        testCategoryOne.setName("updated category");
        CategoryTo updatedCategory = toCategoryTo(testCategoryOne);
        when(categoriesService.update(isA(CategoryTo.class), eq(TEST_CATEGORY_1_ID))).thenReturn(testCategoryOne);

        // When
        mvc
                .perform(put(categoriesPath + "/{id}", TEST_CATEGORY_1_ID)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(updatedCategory))
                )
                .andDo(print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(categoriesService).update(updatedCategory, TEST_CATEGORY_1_ID);
    }

    @Disabled
    @Test
    @SneakyThrows
    void updateInvalid() {
        // Invalid CategoryTo -> BadRequest(400)
    }

    //TODO решить как будет реализовано удаление данных при связанных талицах.
    // А пока не решили - Disabled.
    @Disabled
    @Test
    @Override
    @SneakyThrows
    void delete() {
        // Given
        doNothing().when(categoriesService).delete(TEST_CATEGORY_1_ID);

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(categoriesPath + "/{id}", TEST_CATEGORY_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNoContent())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(categoriesService).delete(TEST_CATEGORY_1_ID);
    }

    @Disabled
    @Test
    @SneakyThrows
    void deleteNotFound() {
        // Given
        int absentedCategoryId = 0;
        doThrow(EmptyResultDataAccessException.class).when(categoriesService).delete(absentedCategoryId);

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(categoriesPath + "/{id}", absentedCategoryId)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(categoriesService).delete(absentedCategoryId);
    }

}
