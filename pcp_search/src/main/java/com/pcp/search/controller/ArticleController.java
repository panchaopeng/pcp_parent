package com.pcp.search.controller;

import com.pcp.search.pojo.Article;
import com.pcp.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @depict: 搜索库中Article的控制层
 * @author: PCP
 * @create: 2019-02-27 17:22
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true, StatusCode.OK,"artucke add");
    }

    @RequestMapping(value = "/search/{key}/{page}/{size}",method = RequestMethod.GET)
    public Result findByTitleOrContentLike(@PathVariable String key,
                                                  @PathVariable int page,
                                                  @PathVariable int size){
        Page<Article> articlePage = articleService.findByTitleOrContentLike(key, page, size);
        return new Result(true,StatusCode.OK,"查询成功",
                new PageResult<Article>(articlePage.getTotalElements(),
                        articlePage.getContent()));
    }
}
