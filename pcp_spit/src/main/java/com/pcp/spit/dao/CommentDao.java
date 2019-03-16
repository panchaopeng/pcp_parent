package com.pcp.spit.dao;

import com.pcp.spit.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentDao extends MongoRepository<Comment,String> {

    List<Comment> findByArticleid(String articleid);

    void  deleteBy_id(String id);
}
