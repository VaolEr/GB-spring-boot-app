package com.example.storehouse.service;

import com.example.storehouse.dto.StorehouseTo;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.repository.StorehousesRepository;
import com.example.storehouse.util.StorehousesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.storehouse.util.ValidationUtil.*;

@Service
@RequiredArgsConstructor
public class StorehousesService {

    private final StorehousesRepository storehousesRepository;

    public List<Storehouse> get(){
        return storehousesRepository.findAll();
    }

    public Storehouse getStorehouseById(Integer id) {
        return checkNotFound(storehousesRepository.findById(id),
                addMessageDetails(Storehouse.class.getSimpleName(), id));
    }

    @Transactional
    public Storehouse create(StorehouseTo storehouseTo){
        Storehouse newStorehouse = StorehousesUtils.fromStorehouseTo(storehouseTo);
        storehouseTo.getItemsStorehousesTo().forEach(iSt -> {
            Storehouse storehouse = checkNotFound(storehousesRepository.findById(iSt.getStorehouseId()),
                    addMessageDetails(Storehouse.class.getSimpleName(), iSt.getStorehouseId())
            );
            ItemStorehouse itemStorehouse = new ItemStorehouse();
            itemStorehouse.setStorehouse(storehouse);
            itemStorehouse.setQuantity(iSt.getQuantity());
            newStorehouse.addItemStorehouse(itemStorehouse);

        });

        return storehousesRepository.save(newStorehouse);
    }

    public Storehouse update(StorehouseTo storehouseTo, Integer id){
        Storehouse updatedStorehouse = StorehousesUtils.fromStorehouseTo(storehouseTo);
        assureIdConsistent(updatedStorehouse, id);
        return storehousesRepository.save(updatedStorehouse);
    }

    public void delete (Integer id){
        storehousesRepository.deleteById(id);
    }

    public Integer getQuantityByItemId(Integer id){
        return storehousesRepository.getQuantityByItemId(id);
    }


}
