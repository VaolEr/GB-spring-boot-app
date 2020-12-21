package com.example.storehouse.repository;

import com.example.storehouse.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CategoriesRepository extends JpaRepository<Category, Integer> {

    Category getByItemsId(Integer itemId);

}
