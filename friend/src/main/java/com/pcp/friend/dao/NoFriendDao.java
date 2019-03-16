package com.pcp.friend.dao;

import com.pcp.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoFriendDao extends JpaRepository<NoFriend,String> {

    NoFriend findByUseridAndFriendid(String user_id, String friend_id);

}
