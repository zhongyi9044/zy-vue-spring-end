package com.zy.service;

import com.zy.pojo.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<Category> getCategoryList();

    Category findCategoryById(Integer id);

    void updateCategory(Category category);

    void deleteCategory(Integer id);
}
