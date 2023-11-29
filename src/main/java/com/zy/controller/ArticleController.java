package com.zy.controller;

import com.zy.pojo.Article;
import com.zy.pojo.Category;
import com.zy.pojo.PageBean;
import com.zy.pojo.Result;
import com.zy.service.ArticleService;
import com.zy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //    新增文章
    @PostMapping
    public Result addArticle(@RequestBody @Validated Article article) {
        articleService.addArticle(article);
        return Result.success();
    }

    //    获取文章列表
    @GetMapping
    public Result<PageBean<Article>> getArticleList(Integer pageNum, Integer pageSize, @RequestParam(required = false) String categoryId, @RequestParam(required = false) String state) {
        PageBean<Article> pageBean = articleService.getArticleList(pageNum, pageSize, categoryId, state);
        return Result.success(pageBean);
    }

//    更新文章
    @PutMapping
    public Result updateArticle(@RequestBody @Validated(Article.Update.class) Article article){
        articleService.updateArticle(article);
        return Result.success();
    }

//    获取文章详情
    @GetMapping("/detail")
    public Result<Article> getArticleDetail(@RequestParam(required = true) Integer id){
        Article article=articleService.getArticleDetail(id);
        return Result.success(article);
    }

//    删除文章
    @DeleteMapping
    public Result deleteArticle(@RequestParam(required = true) Integer id){
        articleService.deleteArticle(id);
        return Result.success();
    }
}
