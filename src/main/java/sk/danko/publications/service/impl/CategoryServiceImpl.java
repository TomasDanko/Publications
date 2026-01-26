package sk.danko.publications.service.impl;

import org.springframework.stereotype.Service;
import sk.danko.publications.entity.AuthorEntity;
import sk.danko.publications.entity.CategoryEntity;
import sk.danko.publications.exception.AuthorNotFoundException;
import sk.danko.publications.exception.CategoryNotFoundException;
import sk.danko.publications.repository.CategoryRepository;
import sk.danko.publications.service.api.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> getALlCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }

    @Override
    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public CategoryEntity updateCategory(long id, CategoryEntity request) {
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(
                ()-> new CategoryNotFoundException("Category is not exists with given id: " + id));

        if(request.getGenre() != null)
            entity.setGenre(request.getGenre());

        if(request.getPublications() != null)
            entity.setPublications(request.getPublications());

        return categoryRepository.save(entity);

    }

    @Override
    public void deleteCategory(long id) {
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category is not exists with given id: " + id));
        categoryRepository.deleteById(id);

    }
}
