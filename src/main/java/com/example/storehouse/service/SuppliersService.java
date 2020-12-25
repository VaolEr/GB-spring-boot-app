package com.example.storehouse.service;

import static com.example.storehouse.util.ItemsUtil.fromItemTo;
import static com.example.storehouse.util.SuppliersUtil.fromSupplierTo;
import static com.example.storehouse.util.ValidationUtil.addMessageDetails;
import static com.example.storehouse.util.ValidationUtil.assureIdConsistent;
import static com.example.storehouse.util.ValidationUtil.checkNotFound;
import static org.springframework.util.StringUtils.hasText;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.ItemStorehouse;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.repository.CategoriesRepository;
import com.example.storehouse.repository.ItemsRepository;
import com.example.storehouse.repository.StorehousesRepository;
import com.example.storehouse.repository.SuppliersRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SuppliersService {

    private final ItemsRepository itemsRepository;
    private final SuppliersRepository suppliersRepository;
    private final CategoriesRepository categoriesRepository;
    private final StorehousesRepository storehousesRepository;

    public List<Supplier> get(String name) {
        return hasText(name) ? suppliersRepository.findByNameContaining(name)
            : suppliersRepository.findAll();
    }

    public Supplier getById(Integer id) {
        return checkNotFound(suppliersRepository.findById(id), addMessageDetails(Supplier.class.getSimpleName(), id));
    }

    public List<Item> getSupplierItems(Integer id){
        return new ArrayList<>(suppliersRepository.getOneById(id).getItems());
    }

    @Transactional
    public Supplier create(SupplierTo supplierTo) {
        //Supplier newSupplier = prepareToSave(supplierTo);
        Supplier newSupplier = fromSupplierTo(supplierTo);
        return suppliersRepository.save(newSupplier);
    }

    @Transactional
    public Supplier update(SupplierTo supplierTo, Integer id) {
        //Supplier updatedSupplier = prepareToSave(supplierTo);
        Supplier updatedSupplier = fromSupplierTo(supplierTo);
        // переделать проверку через HasId, проверять до обработки itemTo
        assureIdConsistent(updatedSupplier, id);
        return suppliersRepository.save(updatedSupplier);
    }

// Не используем, так как при удалении поставщика удалятся и все товары, связанные с ним,
// что недопустимо!
//    @Transactional
//    public void delete(Integer id) {
//        suppliersRepository.deleteById(id);
////        return suppliersRepository.delete(id) != 0;
//    }

    private Supplier prepareToSave(SupplierTo supplierTo) {
        Supplier savedSupplier = fromSupplierTo(supplierTo);
        return savedSupplier;
    }
}
