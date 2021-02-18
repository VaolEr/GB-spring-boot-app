package com.example.storehouse.web.users;

import com.example.storehouse.dto.UserTo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_USER_ID;
import static com.example.storehouse.util.UsersUtil.toUserTo;
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

public class AdminUsersControllerTest extends AbstractUsersControllerTest {
    @BeforeEach
    void setUpAdmin() {
        when(jwtTokenProvider.getAuthentication(AUTH_TOKEN)).thenReturn(mockAuthorize(createTestUserAdmin()));
    }

    @Test
    @Override
    @SneakyThrows
    void create() {
        // Given
        UserTo createdUser = toUserTo(testUserOne);
        when(usersService.create(isA(UserTo.class))).thenReturn(testUserOne);

        // When
        mvc
                .perform(post(usersPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdUser))
                )
                .andDo(print())

                // Then
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                // TODO как бы тут достать URL? Это будет работать в таком виде?
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost" + usersPath + TEST_USER_ID))
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                // TODO поправить проверку содержимого
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(usersService).create(createdUser);
    }

    @Test
    @SneakyThrows
    void createInvalid() {
        // Given
        UserTo createdUser = toUserTo(testUserOne);
        createdUser.setFirstName(null);
        // When
        mvc
                .perform(post(usersPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdUser))
                )
                .andDo(print())

                // Then
                .andExpect(status().isBadRequest())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
    }

    @Test
    @Override
    @SneakyThrows
    void update() {
        // Given
        //testUserOne.setId(999);
        testUserOne.setFirstName("updated_FirstName");
        testUserOne.setLastName("updated_LastName");
        UserTo updatedUser = toUserTo(testUserOne);
        when(usersService.update(isA(UserTo.class), eq(TEST_USER_ID))).thenReturn(testUserOne);

        // When
        mvc
                .perform(put(usersPath + "/{id}", TEST_USER_ID)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(updatedUser))
                )
                .andDo(print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                // TODO поправить проверку содержимого
                .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(usersService).update(updatedUser, TEST_USER_ID);
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
        doNothing().when(usersService).delete(TEST_USER_ID);

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(usersPath + "/{id}", TEST_USER_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNoContent())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(usersService).delete(TEST_USER_ID);
    }

    @Disabled
    @Test
    @SneakyThrows
    void deleteNotFound() {
        // Given
        int absentedUserId = 0;
        doThrow(EmptyResultDataAccessException.class).when(usersService).delete(absentedUserId);

        // When
        mvc
                .perform(MockMvcRequestBuilders.delete(usersPath + "/{id}", absentedUserId)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(usersService).delete(absentedUserId);
    }

}
