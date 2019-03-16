package com.pcp.qa.dao;

import com.pcp.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * problem数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * @depict: 最新回答
     * @param1: labelId
     * @param2: pageable 传入Pageable，自动分页 /{page}/{size}
     * @return: java.util.List<com.pcp.qa.pojo.Problem> {@link java.util.List<com.pcp.qa.pojo.Problem>#}
     * @author: PCP
     * @create: 2019/2/25 19:24
     */
    @Query(value = "select * from tb_problem,tb_pl where id = problemid and labelid = ? order by replytime desc",nativeQuery = true)
    Page<Problem> latest(String labelId, Pageable pageable);

    //最热回答
    @Query(value = "select * from tb_problem,tb_pl where id = problemid and labelid = ? order by reply desc",nativeQuery = true)
    Page<Problem> hotList(String labelId, Pageable pageable);

    //等待回答
    @Query(value = "select * from tb_problem,tb_pl where id = problemid and labelid = ? and reply = 0 order by createtime desc",nativeQuery = true)
    Page<Problem> waitList(String labelId, Pageable pageable);
}
