package com.pcp.spit.controller;

import com.pcp.spit.pojo.Comment;
import com.pcp.spit.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @depict: 评论的控制器类
 * @author: PCP
 * @create: 2019-02-26 19:30
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "comment add");
    }

    @RequestMapping(value = "/article/{articleid}",method = RequestMethod.GET)
    public Result findByArticleid(@PathVariable String articleid){
        return new Result(true,StatusCode.OK,"查询成功",commentService.findByArticleid(articleid));
    }

    @RequestMapping(value = "/article/{id}",method = RequestMethod.DELETE)
    public Result deleteCommentById(@PathVariable String id){
        commentService.deleteBy_id(id);
        return new Result(true,StatusCode.OK,"删除成功");

    }
}
