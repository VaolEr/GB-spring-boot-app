package com.example.storehouse.util;

import com.example.storehouse.dto.UnitTo;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitsUtilTests {
    static final int TEST_UNIT_ID = 21;
    static final String TEST_UNIT_NAME = "TestUnit";

    Unit testUnit;
    UnitTo unitTo;

    @BeforeEach
    public void setUpTests(){

        testUnit = new Unit();
        testUnit.setId(TEST_UNIT_ID);
        testUnit.setName(TEST_UNIT_NAME);
        testUnit.setItems(List.of(new Item()));

        unitTo = UnitsUtil.toUnitTo(testUnit);
    }

    @Test
    public void testToUnitTo() {
        assertEquals(TEST_UNIT_ID, unitTo.getId());
        assertEquals(TEST_UNIT_NAME, unitTo.getName());
    }

    @Test
    public void testToUnitTos() {
        List<Unit> units = new ArrayList<>();

        units.add(testUnit);

        List<UnitTo> unitsTos = UnitsUtil.toUnitTos(units);

        assertEquals(units.get(0).getId(), unitsTos.get(0).getId());
        assertEquals(units.get(0).getName(), unitsTos.get(0).getName());
    }

    @Test
    public void testFromUnitTo() {
        Unit unit = UnitsUtil.fromUnitTo(unitTo);
        assertEquals(TEST_UNIT_NAME, unit.getName()); // because we use only name parameter in function
    }
}
