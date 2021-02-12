package com.example.storehouse.web.suppliers;

import com.example.storehouse.dto.SupplierTo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_SUPPLIER_1_ID;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UnauthorizedSuppliersControllerTest extends AbstractSuppliersControllerTest {

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
                .perform(get(suppliersPath)
                        .headers(headers)
                        .param("name", testSupplierOne.getName())
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(suppliersService);
    }

    @Test
    @Override
    @SneakyThrows
    void getAll() {
        // Given

        // When
        mvc
                .perform(get(suppliersPath)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(suppliersService);
    }



    @Test
    @Override
    @SneakyThrows
    void getById() {
        // Given

        // When
        mvc
                .perform(get(suppliersPath + "/{id}", TEST_SUPPLIER_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(suppliersService);
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
        SupplierTo createdSupplier = toSupplierTo(testSupplierOne);

        // When
        mvc
                .perform(post(suppliersPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdSupplier))
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(suppliersService);
    }

    @Test
    @Override
    @SneakyThrows
    void update() {
        // Given
        SupplierTo updatedSupplier = toSupplierTo(testSupplierOne);

        // When
        mvc
                .perform(put(suppliersPath + "/{id}", TEST_SUPPLIER_1_ID)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(updatedSupplier))
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(suppliersService);
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
                .perform(MockMvcRequestBuilders.delete(suppliersPath + "/{id}", TEST_SUPPLIER_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(suppliersService);
    }

}
