package com.pcp.friend.dao;

import com.pcp.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {

    Friend findByUseridAndFriendid(String user_id, String friend_id);

    @Modifying
    @Query(value = "update tb_friend set islike = ? where userid = ? and friendid = ? ",nativeQuery = true)
    void updateIsLike(String islike, String userid, String friendid);

    @Modifying
    @Query(value = "delete from tb_friend where userid = ? and friendid = ? ",nativeQuery = true)
    void deleteFriend(String user_id, String friendid);

}
