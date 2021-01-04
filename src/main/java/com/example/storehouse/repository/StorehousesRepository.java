package com.example.storehouse.repository;

import com.example.storehouse.model.Item;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.model.Supplier;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface StorehousesRepository extends JpaRepository<Storehouse, Integer> {


    //Storehouse getByItemStorehousesItemId(Integer itemId);

    List<Storehouse> findByNameContaining(String name);

    @EntityGraph(attributePaths = {"items"}, type = EntityGraphType.LOAD)
    Storehouse getOneById(Integer id);

    @Query("select i_s.quantity from ItemStorehouse i_s where i_s.item.id = :itemId")
    Integer getQuantityByItemId(Integer itemId);

}
