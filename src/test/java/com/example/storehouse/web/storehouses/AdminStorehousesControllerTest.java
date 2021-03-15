package com.example.storehouse.web.storehouses;

import com.example.storehouse.dto.StorehouseTo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_STOREHOUSE_1_ID;
import static com.example.storehouse.util.StorehousesUtil.toStorehouseTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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

public class AdminStorehousesControllerTest extends AbstractStorehousesControllerTest {

    @BeforeEach
    void setUpAdmin() {
        when(jwtTokenProvider.getAuthentication(AUTH_TOKEN)).thenReturn(mockAuthorize(createTestUserAdmin()));
    }

    @Test
    @Override
    @SneakyThrows
    void create() {
        // Given
        StorehouseTo createdStorehouse = toStorehouseTo(testStorehouseOne);
        when(storehousesService.create(isA(StorehouseTo.class))).thenReturn(testStorehouseOne);

        // When
        mvc
            .perform(post(storehousesPath)
                .headers(headers)
                .content(objectMapper.writeValueAsString(createdStorehouse))
            )
            .andDo(print())

            // Then
            .andExpect(status().isCreated())
            .andExpect(header().exists(LOCATION))
            .andExpect(header().string(LOCATION, "http://localhost" + storehousesPath + TEST_STOREHOUSE_1_ID))
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(storehousesService).create(createdStorehouse);
    }

    @Test
    @SneakyThrows
    void createInvalid() {
        // Given
        StorehouseTo createdStorehouse = toStorehouseTo(testStorehouseOne);
        createdStorehouse.setName(null);
        // When
        mvc
                .perform(post(storehousesPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdStorehouse))
                )
                .andDo(print())

                // Then
                .andExpect(status().isBadRequest())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(storehousesService);
    }

    @Test
    @Override
    @SneakyThrows
    void update() {
        // Given
        testStorehouseOne.setId(999);
        testStorehouseOne.setName("updated category");
        StorehouseTo updatedStorehouse = toStorehouseTo(testStorehouseOne);
        when(storehousesService.update(isA(StorehouseTo.class), eq(TEST_STOREHOUSE_1_ID))).thenReturn(testStorehouseOne);

        // When
        mvc
                .perform(put(storehousesPath + "/{id}", TEST_STOREHOUSE_1_ID)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(updatedStorehouse))
                )
                .andDo(print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(storehousesService).update(updatedStorehouse, TEST_STOREHOUSE_1_ID);
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
        doNothing().when(storehousesService).delete(TEST_STOREHOUSE_1_ID);

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(storehousesPath + "/{id}", TEST_STOREHOUSE_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNoContent())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(storehousesService).delete(TEST_STOREHOUSE_1_ID);
    }

    @Disabled
    @Test
    @SneakyThrows
    void deleteNotFound() {
        // Given
        int absentedStorehouseId = 0;
        doThrow(EmptyResultDataAccessException.class).when(storehousesService).delete(absentedStorehouseId);

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(storehousesPath + "/{id}", absentedStorehouseId)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(storehousesService).delete(absentedStorehouseId);
    }

}
