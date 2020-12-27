package com.example.storehouse.repository;

import com.example.storehouse.model.Category;
import com.example.storehouse.model.Supplier;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CategoriesRepository extends JpaRepository<Category, Integer> {

    //Category getByItemsId(Integer itemId);

    List<Category> findByNameContaining(String name);

    @EntityGraph(attributePaths = {"items"}, type = EntityGraphType.LOAD)
    Category getOneById(Integer id);
}
