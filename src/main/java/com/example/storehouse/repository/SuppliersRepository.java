package com.example.storehouse.repository;

import com.example.storehouse.model.Item;
import com.example.storehouse.model.Supplier;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface SuppliersRepository extends JpaRepository<Supplier, Integer> {

    Supplier getBySuppliersId(Integer supplierId);

    //@EntityGraph(attributePaths = {"category", "supplier"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Supplier> findByNameContaining(String name);
}
