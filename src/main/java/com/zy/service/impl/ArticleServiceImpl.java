package com.zy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zy.mapper.ArticleMapper;
import com.zy.pojo.Article;
import com.zy.pojo.Category;
import com.zy.pojo.PageBean;
import com.zy.service.ArticleService;
import com.zy.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addArticle(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        article.setCreateTime(LocalDateTime.now());
        Map<String,Object> user= ThreadLocalUtil.get();
        article.setCreateUser((Integer)user.get("id"));
        articleMapper.addArticle(article);
    }

    @Override
    public PageBean<Article> getArticleList(Integer pageNum, Integer pageSize, String categoryId, String state) {
        //创建未来要返回给前端的PageBean对象
        PageBean<Article> pageBean=new PageBean<Article>();

        //利用pageHelper插件进行分页查询，自动会插入到sql语句
        PageHelper.startPage(pageNum,pageSize);

        Map<String,Object> user= ThreadLocalUtil.get();
        Integer userId=(Integer)user.get("id");
        List<Article> articleList = articleMapper.getArticleList(userId,categoryId,state);

        //Page可以自动获取pageHelper里的pageNum(第几页)和pageSize(每页大小)，然后对被强转成Page类型的数据库返还的数据进行分页分装
        Page<Article> page=(Page<Article>) articleList;

        //把分页数据插入到pageBean供前端查看
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public void updateArticle(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.updateArticle(article);
    }

    @Override
    public Article getArticleDetail(Integer id) {
//        Map<String,Object> user= ThreadLocalUtil.get();
//        Integer userId=(Integer)user.get("id");
        Article article=articleMapper.getArticleDetail(id);
        return article;
    }

    @Override
    public void deleteArticle(Integer id) {
        articleMapper.deleteArticle(id);
    }
}
