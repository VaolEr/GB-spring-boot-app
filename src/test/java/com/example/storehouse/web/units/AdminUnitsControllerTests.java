package com.example.storehouse.web.units;

import com.example.storehouse.dto.UnitTo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_UNIT_1_ID;
import static com.example.storehouse.util.UnitsUtil.toUnitTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AdminUnitsControllerTests extends AbstractUnitsControllerTest{
    @BeforeEach
    void setUpAdmin() {
        when(jwtTokenProvider.getAuthentication(AUTH_TOKEN)).thenReturn(mockAuthorize(createTestUserAdmin()));
    }

    @Test
    @Override
    @SneakyThrows
    void create() {
        // Given
        UnitTo createdUnit = toUnitTo(testUnitOne);
        when(unitsService.create(isA(UnitTo.class))).thenReturn(testUnitOne);

        // When
        mvc
                .perform(post(unitsPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdUnit))
                )
                .andDo(print())

                // Then
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                // TODO как бы тут достать URL? Это будет работать в таком виде?
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost" + unitsPath + TEST_UNIT_1_ID))
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                // TODO поправить проверку содержимого
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(unitsService).create(createdUnit);
    }


    @Test
    @SneakyThrows
    void createInvalid() {
        // Given
        UnitTo createdUnit = toUnitTo(testUnitOne);
        createdUnit.setName(null);
        // When
        mvc
                .perform(post(unitsPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdUnit))
                )
                .andDo(print())

                // Then
                .andExpect(status().isBadRequest())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(unitsService);
    }

    @Test
    @Override
    @SneakyThrows
    void update() {
        // Given
        testUnitOne.setId(999);
        testUnitOne.setName("updated unit");
        UnitTo updatedUnit = toUnitTo(testUnitOne);
        when(unitsService.update(isA(UnitTo.class), eq(TEST_UNIT_1_ID))).thenReturn(testUnitOne);

        // When
        mvc
                .perform(put(unitsPath + "/{id}", TEST_UNIT_1_ID)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(updatedUnit))
                )
                .andDo(print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                // TODO поправить проверку содержимого
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(unitsService).update(updatedUnit, TEST_UNIT_1_ID);
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
        doNothing().when(unitsService).delete(TEST_UNIT_1_ID);

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(unitsPath + "/{id}", TEST_UNIT_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNoContent())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(unitsService).delete(TEST_UNIT_1_ID);
    }

    @Disabled
    @Test
    @SneakyThrows
    void deleteNotFound() {
        // Given
        int absentedUnitId = 0;
        doThrow(EmptyResultDataAccessException.class).when(unitsService).delete(absentedUnitId);

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(unitsPath + "/{id}", absentedUnitId)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(unitsService).delete(absentedUnitId);
    }
}
