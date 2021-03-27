package com.example.storehouse.web.users;

import com.example.storehouse.model.User;
import com.example.storehouse.service.UsersService;
import com.example.storehouse.util.exception.NotFoundException;
import com.example.storehouse.web.AbstractControllerTest;
import com.example.storehouse.web.UsersController;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.example.storehouse.TestData.TEST_USER_EMAIL;
import static com.example.storehouse.TestData.TEST_USER_FIRST_NAME;
import static com.example.storehouse.TestData.TEST_USER_ID;
import static com.example.storehouse.TestData.TEST_USER_LAST_NAME;
import static com.example.storehouse.TestData.TEST_USER_PASSWORD;
import static com.example.storehouse.TestData.TEST_USER_ROLE;
import static com.example.storehouse.TestData.TEST_USER_STATUS;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(UsersController.class)
public abstract class AbstractUsersControllerTest extends AbstractControllerTest {

    @Value("${app.endpoints.base_path}" + "${app.endpoints.users.base_url}/")
    String usersPath;

    @MockBean
    UsersService usersService;

    User testUserOne,
        testUserTwo;
    List<User> testUsers;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        createTestEntities();
    }

    @Override
    @Test
    protected void contextLoads() {
        super.contextLoads();
        assertNotNull(usersService);
    }

    @Test
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
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(usersService).get(isNull());
    }

    @Test
    @SneakyThrows
    @DisplayName("Get user by existing id")
    void getById() {
        // Given
        when(usersService.getById(TEST_USER_ID)).thenReturn(testUserOne);

        // When
        mvc
            .perform(get(usersPath + "/{id}", TEST_USER_ID)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.data").isNotEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(usersService).getById(TEST_USER_ID);
    }

    @Test
    @SneakyThrows
    @DisplayName("Get user by not existing id")
    void getByIdNotFound() {
        // Given
        int absentedUserId = 0;
        when(usersService.getById(absentedUserId)).thenThrow(NotFoundException.class);

        // When
        mvc
            .perform(get(usersPath + "/{id}", absentedUserId)
                .headers(headers)
            )
            .andDo(print())

            // Then
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.data").isEmpty())
        ;
        verify(jwtTokenProvider, times(2)).validateToken(AUTH_TOKEN);
        verify(usersService).getById(absentedUserId);
    }

    abstract void create();

    abstract void update();

    abstract void delete();

    void createTestEntities() {

        testUserOne = new User();
        //testUserOne = createTestUserUser();
        testUserOne.setId(TEST_USER_ID);
        testUserOne.setEmail(TEST_USER_EMAIL);
        testUserOne.setPassword(TEST_USER_PASSWORD);
        testUserOne.setFirstName(TEST_USER_FIRST_NAME);
        testUserOne.setLastName(TEST_USER_LAST_NAME);
        testUserOne.setRole(TEST_USER_ROLE);
        testUserOne.setStatus(TEST_USER_STATUS);

        testUserTwo = new User();
        testUserTwo = createTestUserAdmin();

        testUsers = List.of(testUserOne, testUserTwo);

    }
}
