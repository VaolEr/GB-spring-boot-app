package com.example.storehouse.util;

import com.example.storehouse.model.abstractentity.AbstractBaseEntity;
import com.example.storehouse.util.exception.IllegalRequestDataException;
import com.example.storehouse.util.exception.NotFoundException;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtil {

    // https://stackoverflow.com/questions/36166658/how-to-disable-the-optional-used-as-field-or-parameter-type-warning-in-intelli
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> T checkNotFound(@NotNull Optional<T> optional, String msg) {
        return optional.orElseThrow(() -> new NotFoundException("Not found entity with " + msg));
    }

    public static String addMessageDetails(String entityType, Integer entityId) {
        return addMessageDetails(entityType, entityId.toString());
    }

    public static String addMessageDetails(String entityType, String identifier) {
        return String.format("type is '%s' and identifier is '%s'", entityType, identifier);
    }

    public static <E extends AbstractBaseEntity> void assureIdConsistent(E entity, Integer id) {
        // http://stackoverflow.com/a/32728226/548473
        if (entity.isNew()) {
            entity.setId(id);
        } else {
            assert entity.getId() != null;
            if (!entity.getId().equals(id)) {
                throw new IllegalRequestDataException(entity + " must be with id = " + id);
            }
        }
    }

}
