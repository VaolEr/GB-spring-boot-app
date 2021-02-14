package com.example.storehouse.util;

import static org.springframework.util.StringUtils.hasText;

import com.example.storehouse.dto.UserTo;
import com.example.storehouse.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UsersUtil {

    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static UserTo toUserTo(User user) {
        return UserTo
            .builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .role(user.getRole())
            .status(user.getStatus())
            .build();
    }

    public static List<UserTo> toUserTos(List<User> users) {
        return users.stream().map(UsersUtil::toUserTo).collect(Collectors.toList());
    }

    public static User fromUserTo(UserTo userTo) {
        User newUser = new User();
        newUser.setId(userTo.getId());
        newUser.setEmail(userTo.getEmail());
        newUser.setPassword(userTo.getPassword());
        newUser.setFirstName(userTo.getFirstName());
        newUser.setLastName(userTo.getLastName());
        newUser.setRole(userTo.getRole());
        newUser.setStatus(userTo.getStatus());
        return newUser;
    }

    public static User prepareToSave(User user) {
        String password = user.getPassword();
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(hasText(password) ? PASSWORD_ENCODER.encode(password) : password);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setRole(user.getRole());
        user.setStatus(user.getStatus());
        return user;
    }

}
