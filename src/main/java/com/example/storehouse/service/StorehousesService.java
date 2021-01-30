package com.example.storehouse.service;

import static com.example.storehouse.util.ItemsUtil.toItemTos;
import static com.example.storehouse.util.StorehousesUtil.fromStorehouseTo;

import static com.example.storehouse.util.ValidationUtil.addMessageDetails;
import static com.example.storehouse.util.ValidationUtil.assureIdConsistent;
import static com.example.storehouse.util.ValidationUtil.checkNotFound;
import static org.springframework.util.StringUtils.hasText;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.dto.StorehouseTo;

import com.example.storehouse.model.Item;
import com.example.storehouse.model.Storehouse;

import com.example.storehouse.repository.ItemsRepository;
import com.example.storehouse.repository.StorehousesRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StorehousesService {

    private final StorehousesRepository storehousesRepository;
    private final ItemsRepository itemsRepository;

    public List<Storehouse> get(String name) {
        return hasText(name) ? storehousesRepository.findByNameContaining(name)
            : storehousesRepository.findAll();
    }

    public Storehouse getById(Integer id) {
        return checkNotFound(storehousesRepository.findById(id),
                             addMessageDetails(Storehouse.class.getSimpleName(), id));
    }

    @Transactional(readOnly = true)
    public List<ItemTo> getStorehouseItems(Integer storehouseId) {
        //TODO Add check not found storehouse with id
        List<ItemTo> itemTos = toItemTos(itemsRepository.getByItemStorehousesStorehouseId(storehouseId));
        for (ItemTo itemTo:itemTos) {
            itemTo.setTotalQty(storehousesRepository.getItemQuantityOnStorehouseByItemIdAndStorehouseId(itemTo.getId(), storehouseId));
        }
        return itemTos; // work correct
//        return itemsRepository.getStorehouseItemsByStorehouseId(id);

    }

    @Transactional(readOnly = true)
    public Item getStorehouseItem(Integer storehouseId, Integer itemId) {
        return checkNotFound(itemsRepository.getStorehouseItemByStorehouseIdAndItemId(storehouseId, itemId),addMessageDetails(Item.class.getSimpleName(), itemId));
    }

// Prototype func. Realise if need it
//    @Transactional(readOnly = true)
//    public Item getStorehouseItem(Integer storehouseId, String itemName) {
//        //TODO Add check not found storehouse with id
//        return itemsRepository.getStorehouseItemByStorehouseIdAndItemId(storehouseId, itemName);
//    }

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


    @Transactional
    public void delete(Integer id) {
        storehousesRepository.deleteById(id);
    }
}
