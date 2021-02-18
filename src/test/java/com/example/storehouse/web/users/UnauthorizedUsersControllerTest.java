package com.example.storehouse.web.users;

import com.example.storehouse.dto.UserTo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.storehouse.TestData.TEST_USER_ID;
import static com.example.storehouse.util.UsersUtil.toUserTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UnauthorizedUsersControllerTest extends AbstractUsersControllerTest {

    @BeforeEach
    void setUpUnauthorized() {
        when(jwtTokenProvider.validateToken(AUTH_TOKEN)).thenReturn(false);
    }

    @Test
    @SneakyThrows
    void getByEmail() {
        // Given

        // When
        mvc
                .perform(get(usersPath)
                        .headers(headers)
                        .param("email", testUserOne.getEmail())
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
    }

    @Test
    @SneakyThrows
    void getByFirstName() {
        // Given

        // When
        mvc
                .perform(get(usersPath)
                        .headers(headers)
                        .param("first_name", testUserOne.getFirstName())
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
    }

    @Test
    @SneakyThrows
    void getByLastName() {
        // Given

        // When
        mvc
                .perform(get(usersPath)
                        .headers(headers)
                        .param("last_name", testUserOne.getLastName())
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
    }

    @Test
    @SneakyThrows
    void getByRole() {
        // Given

        // When
        mvc
                .perform(get(usersPath)
                        .headers(headers)
                        .param("role", testUserOne.getRole().name())
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
    }

    @Test
    @SneakyThrows
    void getByStatus() {
        // Given

        // When
        mvc
                .perform(get(usersPath)
                        .headers(headers)
                        .param("status", testUserOne.getStatus().name())
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
    }

    @Test
    @Override
    @SneakyThrows
    void getAll() {
        // Given

        // When
        mvc
                .perform(get(usersPath)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
    }



    @Test
    @Override
    @SneakyThrows
    void getById() {
        // Given

        // When
        mvc
                .perform(get(usersPath + "/{id}", TEST_USER_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
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
        UserTo createdUser = toUserTo(testUserOne);

        // When
        mvc
                .perform(post(usersPath)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(createdUser))
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
    }

    @Test
    @Override
    @SneakyThrows
    void update() {
        // Given
        UserTo updatedUser = toUserTo(testUserOne);

        // When
        mvc
                .perform(put(usersPath + "/{id}", TEST_USER_ID)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(updatedUser))
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
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
                .perform(MockMvcRequestBuilders.delete(usersPath + "/{id}", TEST_USER_ID)
                        .headers(headers)
                )
                .andDo(print())

                // Then
                .andExpect(status().isForbidden())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verifyNoInteractions(usersService);
    }

}
