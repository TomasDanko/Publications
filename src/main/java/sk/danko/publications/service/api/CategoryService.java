package sk.danko.publications.service.api;

import sk.danko.publications.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getALlCategories();
    CategoryEntity getCategoryById(Long id);
    CategoryEntity createCategory(CategoryEntity categoryEntity);
    CategoryEntity updateCategory(long id, CategoryEntity entity);
    void deleteCategory(long id);
}
