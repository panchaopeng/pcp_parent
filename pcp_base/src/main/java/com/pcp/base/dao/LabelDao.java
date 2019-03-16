package com.pcp.base.dao;

import com.pcp.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @depict: Label表的CRUD操作接口
 * @author: PCP
 * @create: 2019-02-22 20:51
 */
public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {


    @Modifying
    @Query("update Label label set label.labelname = :labelName where label.id = :id ")
    void updateName(@Param("labelName") String name,
                    @Param("id") String id);
}
