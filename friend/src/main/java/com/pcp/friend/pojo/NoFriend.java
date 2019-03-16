package com.pcp.friend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @depict: friend
 * @author: PCP
 * @create: 2019-03-14 09:41
 */
@Entity
@Table(name = "tb_nofriend")
@IdClass(NoFriend.class) //表示该类为联合主键
public class NoFriend implements Serializable {

    @Id
    private String userid;
    @Id
    private String friendid;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

}
