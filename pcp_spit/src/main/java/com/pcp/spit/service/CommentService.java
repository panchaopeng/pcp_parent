package com.pcp.spit.service;

import com.pcp.spit.dao.CommentDao;
import com.pcp.spit.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;

/**
 * @depict: 评论的控制类
 * @author: PCP
 * @create: 2019-02-26 19:27
 */
@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    public void  add(Comment comment){
        comment.set_id(idWorker.nextId()+"");
        commentDao.save(comment);
    }

    public List<Comment> findByArticleid(String articleid){
        return commentDao.findByArticleid(articleid);
    }


    public void  deleteBy_id(String id){
        commentDao.deleteBy_id(id);
    }
}
