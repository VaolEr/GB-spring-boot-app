package com.example.storehouse.repository;

import com.example.storehouse.model.Storehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface StorehousesRepository extends JpaRepository<Storehouse, Integer> {

    Storehouse getByItemStorehousesItemId(Integer itemId);

    @Query("select i_s.quantity from ItemStorehouse i_s where i_s.item.id = :itemId")
    Integer getQuantityByItemId(Integer itemId);

}
