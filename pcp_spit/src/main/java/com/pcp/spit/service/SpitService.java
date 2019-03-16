package com.pcp.spit.service;

import com.pcp.spit.dao.SpitDao;
import com.pcp.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * @depict: 吐槽的控制类，吐槽的CRUD
 * @author: PCP
 * @create: 2019-02-26 14:04
 */
@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String _id) {
        return spitDao.findById(_id).get();
    }

    public void save(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //如果当前添加的吐槽，有父节点，那么父节点的吐槽回复数要加一
        if (spit.getParentid() != null && !"".equals(spit.getParentid())){

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public void deleteById(String _id) {
        spitDao.deleteById(_id);
    }

    public Page<Spit> findByParentid(String parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentId, pageable);
    }

    public void addThumbup(String spitId) {
        //方式一:低效
        //Spit spit = spitDao.findById(spitId).get();
        //spit.setThumbup((spit.getThumbup() == null ? 0 : spit.getThumbup() )+1);
        //spitDao.save(spit);

        //方式二：使用原生Mongodb实现自增
        //db.spit.update({"_id":"1"},{$inc:{thumbup:NumberInt(1)}})
        //updateFirst,更新第一条匹配的
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId)); //增加查询条件
        Update update = new Update();
        update.inc("thumbup", 1);
        //mongoTemplate.updateFirst(query, update, "spit");//二者等同
        mongoTemplate.updateFirst(query,update,Spit.class);

    }

    public void deleteAll() {
        spitDao.deleteAll();
    }
}
