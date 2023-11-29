package com.zy.controller;

import com.zy.pojo.Category;
import com.zy.pojo.Result;
import com.zy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    添加分类
    @PostMapping
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.addCategory(category);
        return Result.success();
    }

//    获取分类
    @GetMapping
    public Result<List<Category>> getCategory(){
        List<Category> categoryList = categoryService.getCategoryList();
        return Result.success(categoryList);
    }

//    获取分类详情
    @GetMapping("/detail")
    public Result<Category> getCategoryDetail(Integer id){
        Category category=categoryService.findCategoryById(id);
        return Result.success(category);
    }

//    更新分类
    @PutMapping
    public Result updateCategory(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.updateCategory(category);
        return Result.success();
    }

//    删除分类
    @DeleteMapping()
    public Result deleteCategory(Integer id){
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
