package com.example.storehouse.service;

import static com.example.storehouse.util.StorehousesUtil.fromStorehouseTo;

import static com.example.storehouse.util.ValidationUtil.addMessageDetails;
import static com.example.storehouse.util.ValidationUtil.assureIdConsistent;
import static com.example.storehouse.util.ValidationUtil.checkNotFound;
import static org.springframework.util.StringUtils.hasText;

import com.example.storehouse.dto.StorehouseTo;

import com.example.storehouse.model.Item;
import com.example.storehouse.model.Storehouse;

import com.example.storehouse.repository.StorehousesRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StorehousesService {

    private final StorehousesRepository storehousesRepository;

    public List<Storehouse> get(String name) {
        return hasText(name) ? storehousesRepository.findByNameContaining(name)
            : storehousesRepository.findAll();
    }

    public Storehouse getById(Integer id) {
        return checkNotFound(storehousesRepository.findById(id),
                             addMessageDetails(Storehouse.class.getSimpleName(), id));
    }

    public List<Item> getStorehouseItems(Integer id) {
        //TODO Add check not found supplier with id
        return storehousesRepository.getOneById(id).getItems();
    }

    @Transactional
    public Storehouse create(StorehouseTo storehouseTo) {
        Storehouse newStorehouse = fromStorehouseTo(storehouseTo);
        return storehousesRepository.save(newStorehouse);
    }

    @Transactional
    public Storehouse update(StorehouseTo storehouseTo, Integer id) {
        Storehouse updatedStorehouse = fromStorehouseTo(storehouseTo);
        //TODO переделать проверку через HasId
        assureIdConsistent(updatedStorehouse, id);
        return storehousesRepository.save(updatedStorehouse);
    }

}
