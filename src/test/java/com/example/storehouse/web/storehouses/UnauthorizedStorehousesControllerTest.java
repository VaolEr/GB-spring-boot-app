package com.example.storehouse.web.storehouses;

import com.example.storehouse.dto.StorehouseTo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_STOREHOUSE_1_ID;
import static com.example.storehouse.util.StorehousesUtil.toStorehouseTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UnauthorizedStorehousesControllerTest extends AbstractStorehousesControllerTest {

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
                .perform(get(storehousesPath)
                        .headers(headers)
                        .param("name", testStorehouseOne.getName())
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(storehousesService);
    }

    @Test
    @Override
    @SneakyThrows
    void getAll() {
        // Given

        // When
        mvc
                .perform(get(storehousesPath)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(storehousesService);
    }



    @Test
    @Override
    @SneakyThrows
    void getById() {
        // Given

        // When
        mvc
                .perform(get(storehousesPath + "/{id}", TEST_STOREHOUSE_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(storehousesService);
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
        StorehouseTo createdStorehouse = toStorehouseTo(testStorehouseOne);

        // When
        mvc
                .perform(post(storehousesPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdStorehouse))
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(storehousesService);
    }

    @Test
    @Override
    @SneakyThrows
    void update() {
        // Given
        StorehouseTo updatedStorehouse = toStorehouseTo(testStorehouseOne);

        // When
        mvc
                .perform(put(storehousesPath + "/{id}", TEST_STOREHOUSE_1_ID)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(updatedStorehouse))
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(storehousesService);
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
                .perform(MockMvcRequestBuilders.delete(storehousesPath + "/{id}", TEST_STOREHOUSE_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(storehousesService);
    }

}
