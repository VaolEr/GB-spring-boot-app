package com.example.storehouse.repository;

import com.example.storehouse.model.Item;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.model.Supplier;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface StorehousesRepository extends JpaRepository<Storehouse, Integer> {


    //Storehouse getByItemStorehousesItemId(Integer itemId);

    List<Storehouse> findByNameContaining(String name);

    @EntityGraph(attributePaths = {"items"}, type = EntityGraphType.LOAD)
    Storehouse getOneById(Integer id);

    @Query(value = "SELECT SUM(i_s.quantity) FROM ItemStorehouse i_s WHERE i_s.item.id = :itemId")
    Integer getQuantityByItemId(@Param("itemId") Integer itemId);

    @Query(value = "SELECT i_s.quantity FROM ItemStorehouse i_s WHERE i_s.item.id = :itemId AND i_s.storehouse.id = :storehouseId")
    Integer getStorehouseQuantityByItemAndStorehouseIds(@Param("itemId") Integer itemId, @Param("storehouseId") Integer storehouseId);
}
