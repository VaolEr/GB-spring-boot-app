package com.example.storehouse.util;

import com.example.storehouse.dto.UnitTo;
import com.example.storehouse.model.Unit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnitsUtil {

    public static UnitTo toUnitTo(Unit unit) {
        return UnitTo
            .builder()
            .id(unit.getId())
            .name(unit.getName())
            .build();
    }

}
