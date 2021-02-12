package com.example.storehouse.web.units;

import com.example.storehouse.dto.UnitTo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_UNIT_1_ID;
import static com.example.storehouse.util.UnitsUtil.toUnitTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UnauthorizedUnitsControllerTests extends AbstractUnitsControllerTest {

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
                .perform(get(unitsPath)
                        .headers(headers)
                        .param("name", testUnitOne.getName())
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(unitsService);
    }

    @Test
    @Override
    @SneakyThrows
    void getAll() {
        // Given

        // When
        mvc
                .perform(get(unitsPath)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(unitsService);
    }



    @Test
    @Override
    @SneakyThrows
    void getById() {
        // Given

        // When
        mvc
                .perform(get(unitsPath + "/{id}", TEST_UNIT_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(unitsService);
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
        UnitTo createdUnit = toUnitTo(testUnitOne);

        // When
        mvc
                .perform(post(unitsPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdUnit))
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(unitsService);
    }

    @Test
    @Override
    @SneakyThrows
    void update() {
        // Given
        UnitTo updatedUnit = toUnitTo(testUnitOne);

        // When
        mvc
                .perform(put(unitsPath + "/{id}", TEST_UNIT_1_ID)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(updatedUnit))
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(unitsService);
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
                .perform(MockMvcRequestBuilders.delete(unitsPath + "/{id}", TEST_UNIT_1_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(unitsService);
    }

}
