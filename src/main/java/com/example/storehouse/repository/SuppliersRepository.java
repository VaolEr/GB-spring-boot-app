package com.example.storehouse.repository;

import com.example.storehouse.model.Item;
import com.example.storehouse.model.Supplier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface SuppliersRepository extends JpaRepository<Supplier, Integer> {

    //List<Supplier> findAll();

    List<Supplier> findByNameContaining(String name);

    @EntityGraph(attributePaths = {"items"}, type = EntityGraphType.LOAD)
    Supplier getOneById(Integer id);

    //Optional<Supplier> findById(Integer id);
    //Optional<Supplier> getSupplierById(Integer id);
}
