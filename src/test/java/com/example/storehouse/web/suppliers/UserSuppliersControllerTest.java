package com.example.storehouse.web.suppliers;

import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.example.storehouse.TestData.*;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTo;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserSuppliersControllerTest extends AbstractSuppliersControllerTest {

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
    @DisplayName("update not permitted")
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
    @DisplayName("delete not permitted")
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
