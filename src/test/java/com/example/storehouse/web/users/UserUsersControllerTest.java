package com.example.storehouse.web.users;

import com.example.storehouse.dto.UserTo;
import com.example.storehouse.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.example.storehouse.TestData.TEST_USER_ID;
import static com.example.storehouse.util.UsersUtil.toUserTo;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserUsersControllerTest extends AbstractUsersControllerTest{

    @BeforeEach
    void setUpUser() {
        when(jwtTokenProvider.getAuthentication(AUTH_TOKEN)).thenReturn(mockAuthorize(createTestUserUser()));
    }

    @Test
    @Override
    @SneakyThrows
    @DisplayName("Get all users")
    void getAll() {
        // Given
        List<User> users = testUsers;
        when(usersService.get(any())).thenReturn(users);

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
    @DisplayName("Get user by existing id")
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
    @DisplayName("Get user by not existing id")
    void getByIdNotFound() {
        // do nothing
    }

    @Test
    @Override
    @DisplayName("create not permitted")
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
    @DisplayName("update not permitted")
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
    // У нас в принципе не реализован метод delete для Categories, поэтому Disabled временно.
    @Disabled
    @Test
    @Override
    @DisplayName("delete not permitted")
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
