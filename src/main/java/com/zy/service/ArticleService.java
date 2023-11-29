package com.zy.service;

import com.zy.pojo.Article;
import com.zy.pojo.Category;
import com.zy.pojo.PageBean;
import org.apache.ibatis.annotations.Insert;

public interface ArticleService {
    void addArticle(Article article);

    PageBean<Article> getArticleList(Integer pageNum, Integer pageSize, String categoryId, String state);

    void updateArticle(Article article);

    Article getArticleDetail(Integer id);

    void deleteArticle(Integer id);
}
