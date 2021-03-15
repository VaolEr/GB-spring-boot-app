package com.example.storehouse.web.units;

import com.example.storehouse.dto.UnitTo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_UNIT_1_ID;
import static com.example.storehouse.util.UnitsUtil.toUnitTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserUnitsControllerTests extends AbstractUnitsControllerTest {

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
    @DisplayName("update not permitted")
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
    // У нас в принципе не реализован метод delete для Units, поэтому Disabled временно.
    @Disabled
    @Test
    @Override
    @DisplayName("delete not permitted")
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
