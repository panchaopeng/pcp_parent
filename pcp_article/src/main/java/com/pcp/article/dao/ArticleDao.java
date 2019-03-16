package com.pcp.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pcp.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * article数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    //文章审核
    @Modifying
    @Query(value = "update tb_article set state = 1 where id = ?",nativeQuery = true)
    void updateState(String id);


    //文章点赞
    @Modifying
    @Query(value = "update tb_article set thumbup = thumbup + 1 where id = ?",nativeQuery = true)
    void addThumbup(String id);
}
