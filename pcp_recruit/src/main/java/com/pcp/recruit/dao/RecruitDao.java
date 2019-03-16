package com.pcp.recruit.dao;

import com.pcp.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * recruit数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    //查找前6条最新推荐职位
    //where state = ? order by createtime desc
    List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

    //查找前6条最新职位
    //where state ！= ? order by createtime desc
    List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
