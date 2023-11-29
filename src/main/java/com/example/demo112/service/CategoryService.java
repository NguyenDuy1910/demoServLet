package com.example.demo112.service;

import com.example.demo112.dtos.CategoryDTO;
import com.example.demo112.models.Category;
import com.example.demo112.repositories.CategoryRepository;

import java.util.List;

public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository = new CategoryRepository();

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = new Category();
        newCategory.setName(categoryDTO.getName());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long categoryId,
                                   CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Override
    public void deleteCategory(long id) {
        //xóa xong
        categoryRepository.deleteById(id);
    }
}

