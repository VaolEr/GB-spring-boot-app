package com.example.storehouse.web.suppliers;

import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.example.storehouse.TestData.*;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTo;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class AdminSuppliersControllerTest extends AbstractSuppliersControllerTest {
    @BeforeEach
    void setUpAdmin() {
        when(jwtTokenProvider.getAuthentication(AUTH_TOKEN)).thenReturn(mockAuthorize(createTestUserAdmin()));
    }

    @Test
    @Override
    @SneakyThrows
    void create() {
        // Given
        SupplierTo createdSupplier = toSupplierTo(testSupplierOne);
        when(suppliersService.create(isA(SupplierTo.class))).thenReturn(testSupplierOne);

        // When
        mvc
                .perform(post(suppliersPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdSupplier))
                )
                .andDo(print())

                // Then
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                // TODO как бы тут достать URL? Это будет работать в таком виде?
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost" + suppliersPath + TEST_SUPPLIER_1_ID))
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                // TODO поправить проверку содержимого
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(suppliersService).create(createdSupplier);
    }

    @Test
    @SneakyThrows
    void createInvalid() {
        // Given
        SupplierTo createdSupplier = toSupplierTo(testSupplierOne);
        createdSupplier.setName(null);
        // When
        mvc
                .perform(post(suppliersPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdSupplier))
                )
                .andDo(print())

                // Then
                .andExpect(status().isBadRequest())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(suppliersService);
    }

    @Test
    @Override
    @SneakyThrows
    void update() {
        // Given
        testSupplierOne.setId(999);
        testSupplierOne.setName("updated supplier");
        SupplierTo updatedSupplier = toSupplierTo(testSupplierOne);
        when(suppliersService.update(isA(SupplierTo.class), eq(TEST_SUPPLIER_1_ID))).thenReturn(testSupplierOne);

        // When
        mvc
                .perform(put(suppliersPath + "/{id}", TEST_SUPPLIER_1_ID)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(updatedSupplier))
                )
                .andDo(print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                // TODO поправить проверку содержимого
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(suppliersService).update(updatedSupplier, TEST_SUPPLIER_1_ID);
    }

    @Disabled
    @Test
    @SneakyThrows
    void updateInvalid() {
        // Invalid CategoryTo -> BadRequest(400)
    }

    //TODO решить как будет реализовано удаление данных при связанных талицах.
    // У нас в принципе не реализован метод delete для Categories, поэтому Disabled временно.
    @Disabled
    @Test
    @Override
    @SneakyThrows
    void delete() {
        // Given
        doNothing().when(suppliersService).delete(TEST_SUPPLIER_1_ID);

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(suppliersPath + "/{id}", TEST_SUPPLIER_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNoContent())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(suppliersService).delete(TEST_SUPPLIER_1_ID);
    }

    @Disabled
    @Test
    @SneakyThrows
    void deleteNotFound() {
        // Given
        int absentedCategoryId = 0;
        doThrow(EmptyResultDataAccessException.class).when(suppliersService).delete(absentedCategoryId);

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(suppliersPath + "/{id}", absentedCategoryId)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(suppliersService).delete(absentedCategoryId);
    }
}
