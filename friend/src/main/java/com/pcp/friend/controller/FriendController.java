package com.pcp.friend.controller;

import com.pcp.friend.service.FriendService;
import com.pcp.friend.userclient.UserClient;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @depict: friendController
 * @author: PCP
 * @create: 2019-03-14 09:08
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    @PutMapping("/like/{friend_id}/{type}")
    public Result addFriend(@PathVariable("friend_id") String friend_id,
                            @PathVariable("type") String type){
        //验证是否登录，并拿到当前用户的id
        Claims claims = (Claims) request.getAttribute("claim_user");
        if (claims == null){
            //说明当前用户没有user角色
            return new Result(false, StatusCode.LOGIN_ERROR,"权限不足");
        }
        //得到当前用户登录的id
        String user_id = claims.getId();

        //判断是添加好友还是非好友
        if (type != null){

            if ("1".equals(type)){ //添加好友
                int flag = friendService.addFriend(user_id,friend_id);
                if (flag == 0){
                    return new Result(false, StatusCode.ERROR,"不能重复添加好友");

                }
                if (flag == 1){
                    userClient.updateUserIdAndFriendId(user_id,friend_id,1);
                    return new Result(true, StatusCode.OK,"添加成功");

                }

            }else if ("2".equals(type)){//添加非好友,也即把对方打x,这也要记录

                int flag = friendService.addNotFriend(user_id,friend_id);
                if (flag == 0){
                    return new Result(false, StatusCode.ERROR,"不能重复添加非好友");

                }
                if (flag == 1){
                    return new Result(true, StatusCode.OK,"添加成功");

                }
            }
            //type参数错误
            return new Result(false, StatusCode.ERROR,"参数异常");
        }else {
            //type参数异常
            return new Result(false, StatusCode.ERROR,"参数异常");

        }
    }

    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable String friendid){

        //验证是否登录
        Claims claims = (Claims) request.getAttribute("claim_user");
        if (claims == null){
            //说明当前用户没有user角色
            return new Result(false, StatusCode.LOGIN_ERROR,"权限不足");
        }
        //得到当前用户登录的id
        String user_id = claims.getId();
        friendService.deleteFriend(user_id,friendid);
        userClient.updateUserIdAndFriendId(user_id,friendid,-1);
        return new Result(true, StatusCode.OK,"删除成功");


    }
}
