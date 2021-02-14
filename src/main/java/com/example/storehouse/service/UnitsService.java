package com.example.storehouse.service;

import com.example.storehouse.dto.UnitTo;
import com.example.storehouse.model.Unit;
import com.example.storehouse.repository.UnitsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.storehouse.util.UnitsUtil.fromUnitTo;
import static com.example.storehouse.util.ValidationUtil.*;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class UnitsService {

    private final UnitsRepository unitsRepository;

    public List<Unit> get(String name) {
        return hasText(name) ? unitsRepository.findByNameContaining(name)
                : unitsRepository.findAll();
    }

    public Unit getById(Integer id) {
        return checkNotFound(unitsRepository.findById(id),
                addMessageDetails(Unit.class.getSimpleName(), id));
    }
    
    @Transactional
    public Unit create(UnitTo unitTo) {
        Unit newUnit = fromUnitTo(unitTo);
        return unitsRepository.save(newUnit);
    }

    @Transactional
    public Unit update(UnitTo unitTo, Integer id) {
        Unit updatedUnit = fromUnitTo(unitTo);
        //TODO переделать проверку через HasId
        assureIdConsistent(updatedUnit, id);
        return unitsRepository.save(updatedUnit);
    }

    @Transactional
    public void delete(Integer id) {
        unitsRepository.deleteById(id);
    }

}
