package com.pcp.friend.userclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @depict: 模块调用User
 * @author: PCP
 * @create: 2019-03-14 11:08
 */
@Component
@FeignClient("pcp-user")
public interface UserClient {

    @RequestMapping(value = "/user/{userid/{friendid}/{value}",method = RequestMethod.PUT)
    void updateUserIdAndFriendId(@PathVariable("userid") String userid,
                                        @PathVariable("friendid") String friendid,
                                        @PathVariable("value") int value);
}
