package sk.danko.publications.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.danko.publications.entity.AuthorEntity;
import sk.danko.publications.entity.CategoryEntity;
import sk.danko.publications.service.api.CategoryService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    public static final String CATEGORIES_URI = "/categories";
    public static final String CATEGORY_URI = "/category/{id}";
    public static final String CREATE_CATEGORY_URI = "/category";

    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(CATEGORIES_URI)
    public ResponseEntity getAll() {
        Iterable<CategoryEntity> listOfCategories = categoryService.getALlCategories();
        return new ResponseEntity<>(listOfCategories, HttpStatus.OK);
    }

    @GetMapping(CATEGORY_URI)
    public ResponseEntity get(@PathVariable("id") Long id) {
        CategoryEntity entity = categoryService.getCategoryById(id);
        if (entity == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
    }

    @PostMapping(value = CREATE_CATEGORY_URI,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping(value = CATEGORY_URI, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody CategoryEntity request) {

        if (categoryService.getCategoryById(id) != null) {
            categoryService.updateCategory(id, request);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id: " + id + " " + "was not found");
        }


    }

    @DeleteMapping(CATEGORY_URI)
    public ResponseEntity delete(@PathVariable("id") long id) {
        if (categoryService.getCategoryById(id) != null) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id: " + id + " " + "was not found");
        }
    }
}
