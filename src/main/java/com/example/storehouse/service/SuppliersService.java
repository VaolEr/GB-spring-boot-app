package com.example.storehouse.service;

import static com.example.storehouse.util.SuppliersUtil.fromSupplierTo;
import static com.example.storehouse.util.ValidationUtil.addMessageDetails;
import static com.example.storehouse.util.ValidationUtil.assureIdConsistent;
import static com.example.storehouse.util.ValidationUtil.checkNotFound;
import static org.springframework.util.StringUtils.hasText;

import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.repository.SuppliersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SuppliersService {

    private final SuppliersRepository suppliersRepository;

    public List<Supplier> get(String name) {
        return hasText(name) ? suppliersRepository.findByNameContaining(name)
            : suppliersRepository.findAll();
    }

    public Supplier getById(Integer id) {
        return checkNotFound(suppliersRepository.findById(id),
            addMessageDetails(Supplier.class.getSimpleName(), id));
    }

    public List<Item> getSupplierItems(Integer id) {
        //TODO Add check not found supplier with id
        return suppliersRepository.getOneById(id).getItems();
    }

    @Transactional
    public Supplier create(SupplierTo supplierTo) {
        Supplier newSupplier = fromSupplierTo(supplierTo);
        return suppliersRepository.save(newSupplier);
    }

    @Transactional
    public Supplier update(SupplierTo supplierTo, Integer id) {
        Supplier updatedSupplier = fromSupplierTo(supplierTo);
        //TODO переделать проверку через HasId
        assureIdConsistent(updatedSupplier, id);
        return suppliersRepository.save(updatedSupplier);
    }

}
