package com.pcp.friend.service;

import com.pcp.friend.dao.FriendDao;
import com.pcp.friend.dao.NoFriendDao;
import com.pcp.friend.pojo.Friend;
import com.pcp.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @depict: friendService
 * @author: PCP
 * @create: 2019-03-14 09:28
 */
@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String user_id, String friend_id) {
        //1.判断user_id到friend_id是否有数据，有就是重复添加，返回0
        Friend friend = friendDao.findByUseridAndFriendid(user_id, friend_id);
        if (friend != null){
            return 0;
        }
        //2.如果不是重复添加，则直接添加数据。让好友表中的user_id到friend_id方向的type为0。也即单向喜欢

        friend = new Friend();
        friend.setUserid(user_id);
        friend.setFriendid(friend_id);
        friend.setIslike("0");
        friendDao.save(friend);

        //3.判断friend_id到user_id是否有数据，有的话说明是双向喜欢，把双方状态都改为1
        //== null 表示没有这条记录
        //!= null 表示有记录，也即代表双向喜欢
        if (friendDao.findByUseridAndFriendid(friend_id, user_id) != null){
            //把双方的isLike都改为1
            friendDao.updateIsLike("1",user_id,friend_id);
            friendDao.updateIsLike("1",friend_id,user_id);
        }
        return 1;
    }

    public int addNotFriend(String user_id, String friend_id) {
        //先判断是否已经是非好友
        //== null 代表没有记录，则可以添加好友
        //!= null 代表记录存在，不能重复添加
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(user_id,friend_id);
        if (noFriend != null){
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(user_id);
        noFriend.setFriendid(friend_id);
        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String user_id, String friendid) {
        //删除表中user_id到friendid的记录
        friendDao.deleteFriend(user_id,friendid);

        //更新friendid到user_id中的isLike为0
        friendDao.updateIsLike("0",friendid,user_id);

        //非好友表中添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(user_id);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
