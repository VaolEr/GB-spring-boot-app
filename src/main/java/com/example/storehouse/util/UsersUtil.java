package com.example.storehouse.util;

import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.dto.UserTo;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsersUtil {

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
        newUser.setEmail(userTo.getEmail());
        newUser.setPassword(userTo.getPassword());
        newUser.setFirstName(userTo.getFirstName());
        newUser.setLastName(userTo.getLastName());
        newUser.setRole(userTo.getRole());
        newUser.setStatus(userTo.getStatus());
        return newUser;
    }
}
