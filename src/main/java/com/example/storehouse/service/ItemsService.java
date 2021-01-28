package com.example.storehouse.service;

import static com.example.storehouse.util.ItemsUtil.fromItemTo;
import static com.example.storehouse.util.ValidationUtil.addMessageDetails;
import static com.example.storehouse.util.ValidationUtil.assureIdConsistent;
import static com.example.storehouse.util.ValidationUtil.checkNotFound;
import static org.springframework.util.StringUtils.hasText;

import com.example.storehouse.dto.ItemStorehouseTo;
import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.model.Unit;
import com.example.storehouse.repository.CategoriesRepository;
import com.example.storehouse.repository.ItemsRepository;
import com.example.storehouse.repository.StorehousesRepository;
import com.example.storehouse.repository.SuppliersRepository;
import com.example.storehouse.repository.UnitsRepository;
import com.example.storehouse.util.ItemsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemsService {

    private final ItemsRepository itemsRepository;
    private final SuppliersRepository suppliersRepository;
    private final CategoriesRepository categoriesRepository;
    private final StorehousesRepository storehousesRepository;
    private final UnitsRepository unitsRepository;

    public Page<ItemTo> get(Pageable pageable, String name) {
        Page<Item> itemToPage = hasText(name) ? itemsRepository.findByNameContaining(name, pageable)
                : itemsRepository.findAll(pageable);

        Page<ItemTo> itemToPages = itemToPage.map(ItemsUtil::toItemTo);

        for (ItemTo itemTo : itemToPages) {
            itemTo.setTotalQty(storehousesRepository.getQuantityByItemId(itemTo.getId()));
        }

        return itemToPages;
    }

    public Item getById(Integer id) {
        return checkNotFound(itemsRepository.findById(id), addMessageDetails(Item.class.getSimpleName(), id));
    }

    @Transactional
    public Item create(ItemTo itemTo) {
        Item newItem = prepareToSave(itemTo);
        // NOTE попробовать это место посимпатичней сделать
        itemTo.getItemsStorehouses().forEach(iSt -> {
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

        List<Integer> presentStorehouses = storehousesRepository.getCountOfStorehousesWhereItemPresent(updatedItem.getId());

        itemTo.getItemsStorehouses().forEach(iSt -> {
            Storehouse storehouse = checkNotFound(storehousesRepository.findById(iSt.getStorehouseId()),
                    addMessageDetails(Storehouse.class.getSimpleName(), iSt.getStorehouseId())
            );
            if (presentStorehouses.size() != 0) {
                for (Integer storehouseId : presentStorehouses) {
                    if (storehouseId.equals(storehouse.getId())) {
                        Integer itemStorehouseId = storehousesRepository.getItemStorehousesById(updatedItem.getId(), storehouse.getId());
                        storehousesRepository.updateItemQtyByItemStorehouseIdAndStorehouseIdAndItemId(updatedItem.getId(), storehouse.getId(), itemStorehouseId, iSt.getQuantity());
                    } else {
                        //Можно вынести в отдельный метод
                        ItemStorehouse itemStorehouse = new ItemStorehouse();
                        itemStorehouse.setStorehouse(storehouse);
                        itemStorehouse.setQuantity(iSt.getQuantity());
                        updatedItem.addItemStorehouse(itemStorehouse);
                    }
                }
            } else {
                ItemStorehouse itemStorehouse = new ItemStorehouse();
                itemStorehouse.setStorehouse(storehouse);
                itemStorehouse.setQuantity(iSt.getQuantity());
                updatedItem.addItemStorehouse(itemStorehouse);
            }
        });
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
        savedItem.setUnit(
                checkNotFound(unitsRepository.findById(itemTo.getUnit().getId()),
                        addMessageDetails(Unit.class.getSimpleName(), itemTo.getUnit().getId())
                ));
        itemTo.getCategories()
                .forEach(categoryTo -> savedItem.setCategory(checkNotFound(categoriesRepository.findById(categoryTo.getId()),
                        addMessageDetails(Category.class.getSimpleName(), categoryTo.getId())))
                );
        return savedItem;
    }

}
