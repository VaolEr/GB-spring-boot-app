package com.example.storehouse.service;

import static com.example.storehouse.util.ItemsUtil.toItemTo;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.Item;
import com.example.storehouse.repository.ItemsRepository;
import com.example.storehouse.repository.StorehousesRepository;
import com.example.storehouse.util.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemsService {

    private final ItemsRepository itemsRepository;
    private final StorehousesRepository storehousesRepository;

    public List<Item> get(String name) {
        return (name == null) ?
            itemsRepository.findAll() :
            itemsRepository.findByNameLike(name);
    }

    public Item getById(Integer id) {
        return itemsRepository.findById(id).orElseThrow(
            () -> new NotFoundException(
                String.format("Not found '%s' with id '%d'", Item.class.getSimpleName(), id)
            )
        );
    }

    // Не очень нравится мне такой вариант, надо поискать, как через ORM сделать покрасивей
    @Transactional
    public ItemTo getByIdWithBalance(Integer id) {
        Item item = itemsRepository.getItemById(id).orElseThrow(
            () -> new NotFoundException(String.valueOf(id))
        );
        Integer itemId = item.getId();
        ItemTo itemTo = toItemTo(item);
        itemTo.setCategoryId(item.getCategory().getId());
        itemTo.setSupplierId(item.getSupplier().getId());
        itemTo.setStorehouseId(
            storehousesRepository.getByItemStorehousesItemId(itemId).getId());
        itemTo.setQuantity(storehousesRepository.getQuantityByItemId(itemId));

        return itemTo;
    }

}
