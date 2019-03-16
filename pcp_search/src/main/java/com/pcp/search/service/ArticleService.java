package com.pcp.search.service;

import com.pcp.search.dao.ArticleDao;
import com.pcp.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @depict: 搜索库的服务层
 * @author: PCP
 * @create: 2019-02-27 17:16
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;


    //@Autowired
    //private IdWorker idWorker;

    public void save(Article article){
        //搜索库会自动生成id，可以不用IdWoker
        //article.setId(idWorker.nextId()+"");
        articleDao.save(article);
    }

    public Page<Article> findByTitleOrContentLike(String key,int page,int size){

        Pageable pageable = PageRequest.of(page-1,size);
        return articleDao.findByTitleOrContentLike(key,key,pageable);
    }
}
