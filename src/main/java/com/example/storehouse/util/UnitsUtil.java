package com.example.storehouse.util;

import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.dto.UnitTo;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.model.Unit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnitsUtil {

    public static UnitTo toUnitTo(Unit unit) {
        return UnitTo
                .builder()
                .id(unit.getId())
                .name(unit.getName())
                .build();
    }

    public static List<UnitTo> toUnitTos(List<Unit> units) {
        return units.stream().map(UnitsUtil::toUnitTo).collect(Collectors.toList());
    }

    public static Unit fromUnitTo(UnitTo unitTo) {
        Unit newUnit = new Unit();
        newUnit.setId(unitTo.getId());
        newUnit.setName(unitTo.getName());
        return newUnit;
    }

}
