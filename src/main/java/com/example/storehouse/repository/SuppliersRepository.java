package com.example.storehouse.repository;

import com.example.storehouse.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface SuppliersRepository extends JpaRepository<Supplier, Integer> {

    Supplier getByItemsId(Integer itemId);

}
