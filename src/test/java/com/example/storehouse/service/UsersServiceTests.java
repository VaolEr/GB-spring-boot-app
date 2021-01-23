package com.example.storehouse.service;

import static com.example.storehouse.service.ServiceTestData.TEST_USER_EMAIL;
import static com.example.storehouse.service.ServiceTestData.TEST_USER_FIRST_NAME;
import static com.example.storehouse.service.ServiceTestData.TEST_USER_ID;
import static com.example.storehouse.service.ServiceTestData.TEST_USER_LAST_NAME;
import static com.example.storehouse.service.ServiceTestData.TEST_USER_PASSWORD;
import static com.example.storehouse.service.ServiceTestData.TEST_USER_ROLE;
import static com.example.storehouse.service.ServiceTestData.TEST_USER_STATUS;
import static com.example.storehouse.util.UsersUtil.fromUserTo;
import static com.example.storehouse.util.UsersUtil.toUserTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.storehouse.dto.UserTo;
import com.example.storehouse.model.User;
import com.example.storehouse.repository.UsersRepository;
import com.example.storehouse.util.exception.IllegalRequestDataException;
import com.example.storehouse.util.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(UsersService.class)
public class UsersServiceTests {

    @Autowired
    UsersService service;

    @MockBean
    private UsersRepository usersRepository;

    User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(TEST_USER_ID);
        testUser.setEmail(TEST_USER_EMAIL);
        testUser.setPassword(TEST_USER_PASSWORD);
        testUser.setFirstName(TEST_USER_FIRST_NAME);
        testUser.setLastName(TEST_USER_LAST_NAME);
        testUser.setRole(TEST_USER_ROLE);
        testUser.setStatus(TEST_USER_STATUS);
    }

    @DisplayName("Should return user where email equals TEST_USER_EMAIL")
    @Test
    void getByEmail() {
        // Given
        when(usersRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        // When
        List<User> returnedUsers = service.get(testUser.getEmail());

        // Then
        verify(usersRepository).findByEmail(testUser.getEmail());
        assertThat(returnedUsers).containsExactly(testUser);
    }

    @DisplayName("Have non-existent email, must thrown NotFoundException")
    @Test
    void getByEmailAndEmailAbsent() {
        // Given
        String userEmail = "absent email";
        when(usersRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        // When
        assertThatThrownBy(() -> service.get(userEmail))

            // Then
            .isInstanceOfSatisfying(NotFoundException.class,
                                    e -> assertEquals("Not found entity with type is 'User' and identifier is 'absent email'", e.getMessage())
            );
    }

    @DisplayName("Should return user where id equals TEST_USER_ID")
    @Test
    void getById() {
        // Given
        when(usersRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));

        // When
        User returnedUsers = service.getById(TEST_USER_ID);

        // Then
        //assert testUser.getId() != null;
        verify(usersRepository).findById(TEST_USER_ID);
        assertEquals(returnedUsers, testUser);
    }

    @DisplayName("Have non-existent User id, must thrown NotFoundException")
    @Test
    void getByIdAndIdAbsent() {
        // Given
        Integer userId = 0;

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(userId))

            // Then
            .isInstanceOfSatisfying(NotFoundException.class,
                                    e -> assertEquals("Not found entity with type is 'User' and identifier is '0'", e.getMessage())
            );
    }

    @DisplayName("Should create new User from UserTo and return it")
    @Test
    void createIsOk() {
        // Given
        UserTo newUserTo = UserTo.builder()
                                         .email("NewEmail")
                                         .password("newPassword")
                                         .build();
        User newUser = fromUserTo(newUserTo);
        when(usersRepository.save(newUser)).thenReturn(newUser);

        // When
        User createdUser = service.create(newUserTo);

        // Then
        verify(usersRepository).save(newUser);
        assertEquals(createdUser, newUser);
    }

    @DisplayName("Should correctly update user where User.id is equal to /path/{id}")
    @Test
    void updateIsOkAndUserIdIsConsistent() {
        // Given
        int userId = 411;
        int pathId = 411;
        UserTo updatedUserTo = UserTo.builder()
                                                 .id(userId)
                                                 .email("UpdatedEmail")
                                                 .password("updatedPassword")
                                                 .build();
        User updatedUser = fromUserTo(updatedUserTo);
        when(usersRepository.save(updatedUser)).thenReturn(updatedUser);

        // When
        User savedUser = service.update(updatedUserTo, pathId);

        // Then
        verify(usersRepository).save(updatedUser);
        assertEquals(updatedUser, savedUser);
        assertEquals(savedUser.getId(), pathId);
    }

    @DisplayName("Have User.id is not equal to /path/{id}, must thrown IllegalRequestDataException")
    @Test
    void updateIsFailedAndItemIdNotConsistent() {
        // Given
        int userId = 411;
        int pathId = 511;
        UserTo updatedUserTo = toUserTo(testUser);
        updatedUserTo.setId(userId);
        User updatedCategory = fromUserTo(updatedUserTo);
        when(usersRepository.save(updatedCategory)).thenReturn(updatedCategory);

        // When
        assertThatThrownBy(() -> service.update(updatedUserTo, pathId))

            // Then
            .isInstanceOfSatisfying(IllegalRequestDataException.class,
                                    e -> assertEquals(String.format("AbstractBaseEntity(id=%d) must be with id = %d", userId, pathId), e.getMessage())
            );
    }

    @Test
    void deleteIsOk() {
        // Given

        // When
        service.delete(TEST_USER_ID);

        // Then
        verify(usersRepository).deleteById(TEST_USER_ID);
    }

}
