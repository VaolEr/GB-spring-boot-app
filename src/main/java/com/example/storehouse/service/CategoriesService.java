package com.example.storehouse.service;

import static com.example.storehouse.util.CategoriesUtil.fromCategoryTo;
import static com.example.storehouse.util.SuppliersUtil.fromSupplierTo;
import static com.example.storehouse.util.ValidationUtil.addMessageDetails;
import static com.example.storehouse.util.ValidationUtil.assureIdConsistent;
import static com.example.storehouse.util.ValidationUtil.checkNotFound;
import static org.springframework.util.StringUtils.hasText;

import com.example.storehouse.dto.CategoryTo;
import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.repository.CategoriesRepository;
import com.example.storehouse.repository.ItemsRepository;
import com.example.storehouse.repository.StorehousesRepository;
import com.example.storehouse.repository.SuppliersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final ItemsRepository itemsRepository;
    //private final SuppliersRepository suppliersRepository;
    private final CategoriesRepository categoriesRepository;
    private final StorehousesRepository storehousesRepository;

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
        // переделать проверку через HasId, проверять до обработки itemTo
        assureIdConsistent(updatedCategory, id);
        return categoriesRepository.save(updatedCategory);
    }

// Не используем, так как при удалении поставщика удалятся и все товары, связанные с ним,
// что недопустимо!
//    @Transactional
//    public void delete(Integer id) {
//        suppliersRepository.deleteById(id);
////        return suppliersRepository.delete(id) != 0;
//    }

    private Category prepareToSave(CategoryTo categoryTo) {
        Category savedCategory = fromCategoryTo(categoryTo);
        return savedCategory;
    }

}
