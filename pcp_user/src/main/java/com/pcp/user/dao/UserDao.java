package com.pcp.user.dao;

import com.pcp.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * user数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    User findByMobile(String mobile);

    //更新用户关注数
    @Modifying
    @Query(value = "update tb_user set followcount = ? where id = ? ",nativeQuery = true)
    void updateUserFollwCount(int new_value,String user_id);

    //更新好友粉丝数
    @Modifying
    @Query(value = "update tb_user set fanscount = ? where id = ? ",nativeQuery = true)
    void updateUserFansCount(int new_value,String friend_id);
}
