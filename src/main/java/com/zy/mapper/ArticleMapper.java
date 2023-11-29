package com.zy.mapper;

import com.zy.pojo.Article;
import com.zy.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article (title, content, cover_img, state, category_id, create_user, create_time, update_time) VALUES (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void addArticle(Article article);

    //categoryId和state不一定需要，所以使用映射配置文件
    List<Article> getArticleList(Integer userId, String categoryId, String state);

    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id=#{id}")
    void updateArticle(Article article);

    @Select("select *from article where id=#{id}")
    Article getArticleDetail( Integer id);

    @Delete("delete from article where id=#{id}")
    void deleteArticle(Integer id);
}
