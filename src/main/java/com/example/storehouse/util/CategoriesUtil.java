package com.example.storehouse.util;

import com.example.storehouse.dto.CategoryTo;
import com.example.storehouse.model.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategoriesUtil {

    // NOTE: не забыть проверить необходимость этого метода, если будет исп. EntityGraph
    public static CategoryTo toCategoryTo(Category category) {
        return CategoryTo
            .builder()
            .id(category.getId())
            .name(category.getName())
            .build();
    }

    public static List<CategoryTo> toCategoryTos(List<Category> categories) {
        return categories.stream().map(CategoriesUtil::toCategoryTo).collect(Collectors.toList());
    }

    public static Category fromCategoryTo(CategoryTo categoryTo) {
        Category newCategory = new Category();
        newCategory.setId(categoryTo.getId());
        newCategory.setName(categoryTo.getName());
        return newCategory;
    }

}
