package com.zy.service.impl;

import com.zy.mapper.CategoryMapper;
import com.zy.pojo.Category;
import com.zy.service.CategoryService;
import com.zy.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String,Object> claims= ThreadLocalUtil.get();
        Integer userId=(Integer) claims.get("id");
        category.setCreateUser(userId);

        categoryMapper.addCategory(category);
    }

    @Override
    public List<Category> getCategoryList() {
        Map<String,Object> claims= ThreadLocalUtil.get();
        Integer userId=(Integer) claims.get("id");
        List<Category> categoryList=categoryMapper.getCategoryList(userId);
        return categoryList;
    }

    @Override
    public Category findCategoryById(Integer id) {
        Category category=categoryMapper.findCategoryById(id);
        return category;
    }

    @Override
    public void updateCategory(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryMapper.deleteCategory(id);
    }


}
