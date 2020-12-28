package com.example.storehouse.service;

import static com.example.storehouse.util.ItemsUtil.fromItemTo;
import static com.example.storehouse.util.ValidationUtil.addMessageDetails;
import static com.example.storehouse.util.ValidationUtil.assureIdConsistent;
import static com.example.storehouse.util.ValidationUtil.checkNotFound;
import static org.springframework.util.StringUtils.hasText;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.repository.CategoriesRepository;
import com.example.storehouse.repository.ItemsRepository;
import com.example.storehouse.repository.StorehousesRepository;
import com.example.storehouse.repository.SuppliersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemsService {

    private final ItemsRepository itemsRepository;
    private final SuppliersRepository suppliersRepository;
    private final CategoriesRepository categoriesRepository;
    private final StorehousesRepository storehousesRepository;

    public List<Item> get(String name) {
        return hasText(name) ? itemsRepository.findByNameContaining(name)
            : itemsRepository.findAll();
    }

    public Item getById(Integer id) {
        return checkNotFound(itemsRepository.findById(id), addMessageDetails(Item.class.getSimpleName(), id));
    }

    @Transactional
    public Item create(ItemTo itemTo) {
        Item newItem = prepareToSave(itemTo);
        // NOTE попробовать это место посимпатичней сделать
        itemTo.getItemsStorehousesTo().forEach(iSt -> {
            Storehouse storehouse = checkNotFound(storehousesRepository.findById(iSt.getStorehouseId()),
                addMessageDetails(Storehouse.class.getSimpleName(), iSt.getStorehouseId())
            );
            ItemStorehouse itemStorehouse = new ItemStorehouse();
            itemStorehouse.setStorehouse(storehouse);
            itemStorehouse.setQuantity(iSt.getQuantity());
            newItem.addItemStorehouse(itemStorehouse);
        });
        return itemsRepository.save(newItem);
    }

    // обновление остатков на складах - через запрос к контроллеру складов
    @Transactional
    public Item update(ItemTo itemTo, Integer id) {
        Item updatedItem = prepareToSave(itemTo);
        // TODO переделать проверку через HasId, проверять до обработки itemTo
        assureIdConsistent(updatedItem, id);
        return itemsRepository.save(updatedItem);
    }

    @Transactional
    public void delete(Integer id) {
        itemsRepository.deleteById(id);
    }

    private Item prepareToSave(ItemTo itemTo) {
        Item savedItem = fromItemTo(itemTo);
        savedItem.setSupplier(
            checkNotFound(suppliersRepository.findById(itemTo.getSupplier().getId()),
                addMessageDetails(Supplier.class.getSimpleName(), itemTo.getSupplier().getId())
            ));
        itemTo.getCategories()
            .forEach(categoryTo -> savedItem.setCategory(checkNotFound(categoriesRepository.findById(categoryTo.getId()),
                addMessageDetails(Category.class.getSimpleName(), categoryTo.getId())))
            );
        return savedItem;
    }

}
