package com.example.storehouse.service;

import com.example.storehouse.dto.CategoryTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.storehouse.util.CategoriesUtil.fromCategoryTo;
import static com.example.storehouse.util.ValidationUtil.*;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public List<Category> get(String name) {
        return hasText(name) ? categoriesRepository.findByNameContaining(name)
                : categoriesRepository.findAll();
    }

    public Category getById(Integer id) {
        return checkNotFound(categoriesRepository.findById(id),
                addMessageDetails(Category.class.getSimpleName(), id));
    }

    public List<Item> getCategoryItems(Integer id) {
        //TODO Add check not found category with id
        return categoriesRepository.getOneById(id).getItems();
    }

    @Transactional
    public Category create(CategoryTo categoryTo) {
        Category newCategory = fromCategoryTo(categoryTo);
        return categoriesRepository.save(newCategory);
    }

    @Transactional
    public Category update(CategoryTo categoryTo, Integer id) {
        Category updatedCategory = fromCategoryTo(categoryTo);
        //TODO переделать проверку через HasId
        assureIdConsistent(updatedCategory, id);
        return categoriesRepository.save(updatedCategory);
    }

}
