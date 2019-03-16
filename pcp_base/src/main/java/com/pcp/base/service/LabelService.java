package com.pcp.base.service;

import com.pcp.base.dao.LabelDao;
import com.pcp.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @depict: 实现LabelDao接口的服务
 * @author: PCP
 * @create: 2019-02-22 20:59
 */
@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;
    
    @Autowired
    private IdWorker idWorker;

    /**
     * @depict: 查找所有Label数据
     * @return: java.util.List<com.pcp.base.pojo.Label> {@link List< Label>}
     * @author: PCP
     * @create: 2019/2/22 21:03
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * @depict: 根据labelId返回一个Label对象
     * @param1: labelId {@link Label#id}
     * @return: com.pcp.base.pojo.Label {@link Label#}
     * @author: PCP
     * @create: 2019/2/22 21:05
     */
    public Label findById(String labelId){
        return labelDao.findById(labelId).get();
    }
    
    /**
     * @depict: 保存一个Label对象。先去数据库查找id，若id不存在，则保存数据
     * @param1: label {@link Label}
     * @author: PCP
     * @create: 2019/2/22 21:10
     */
    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * @depict: 更新一个Label对象。先去数据库查找id，若id存在，则更新数据
     * @return: void
     * @author: PCP
     * @create: 2019/2/22 21:12
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * @depict: 根据Label id删除数据
     * @param1: labelId  {@link Label#id}
     * @return: void
     * @author: PCP
     * @create: 2019/2/22 21:17
     */
    public void deleteById(String labelId){
        labelDao.deleteById(labelId);
    }


    public void updateName(String name,String id){
        labelDao.updateName(name,id);
    }

    public List<Label> findSearch(Label label){
        return labelDao.findAll(new Specification<Label>() {
            /**
             * @depict: 条件查询
             * @param1: root 根对象，也就是要把条件封装到哪个对象中。where 属性名 = label.getId
             * @param2: query 封装的都是查询关键字，比如group by , order by等。一般用不到这个
             * @param3: cb 用来封装条件对象的，如果直接返回null，表示不需要任何条件
             * @return: javax.persistence.criteria.Predicate {@link Predicate}
             * @author: PCP
             * @create: 2019/2/25 10:31
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                //用来存放所有条件
                List<Predicate> predicateList = new ArrayList<>();

                if (label.getLabelname() !=null && !"".equals(label.getLabelname())){
                    //相当于 labelname like %小明%
                    Predicate predicate = cb.like(root.get("labelname").as(String.class),"%"+ label.getLabelname() + "%");
                    predicateList.add(predicate);
                }

                if (label.getState() !=null && !"".equals(label.getState())){
                    //相当于 state = "1"
                    Predicate predicate = cb.equal(root.get("state").as(String.class),label.getState());
                    predicateList.add(predicate);

                }

                //将所有条件转化为一个数组
                Predicate[] predicates = new Predicate[predicateList.size()];
                predicateList.toArray(predicates);

                //相当于 where labelname like %小明% and state = "1"
                return cb.and(predicates);
            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {

        Pageable pageable = PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {

            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                //用来存放所有条件
                List<Predicate> predicateList = new ArrayList<>();

                if (label.getLabelname() !=null && !"".equals(label.getLabelname())){
                    //相当于 labelname like %小明%
                    Predicate predicate = cb.like(root.get("labelname").as(String.class),"%"+ label.getLabelname() + "%");
                    predicateList.add(predicate);
                }

                if (label.getState() !=null && !"".equals(label.getState())){
                    //相当于 state = "1"
                    Predicate predicate = cb.equal(root.get("state").as(String.class),label.getState());
                    predicateList.add(predicate);

                }

                //将所有条件转化为一个数组
                Predicate[] predicates = new Predicate[predicateList.size()];
                predicateList.toArray(predicates);

                //相当于 where labelname like %小明% and state = "1"
                return cb.and(predicates);
            }
        },pageable);
    }
}
