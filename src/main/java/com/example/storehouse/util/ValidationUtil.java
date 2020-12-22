package com.example.storehouse.util;

import com.example.storehouse.util.exception.NotFoundException;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtil {

    // https://stackoverflow.com/questions/36166658/how-to-disable-the-optional-used-as-field-or-parameter-type-warning-in-intelli
    public static <T> T checkNotFound(@NotNull Optional<T> optional, String msg) {
        return optional.orElseThrow(() -> new NotFoundException("Not found entity with " + msg));
    }

    public static String addMessageDetails(String entityType, Integer entityId) {
        return String.format("type is '%s' and id is '%d'", entityType, entityId);
    }

}
