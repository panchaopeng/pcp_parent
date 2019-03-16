package com.pcp.user.controller;

import com.pcp.user.pojo.User;
import com.pcp.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * user控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    //更新用户关注数和好友粉丝数
    @RequestMapping(value = "/{userid/{friendid}/{value}",method = RequestMethod.PUT)
    public void updateUserIdAndFriendId(@PathVariable String userid,
                                        @PathVariable String friendid,
                                        @PathVariable int value){

        userService.updateUserIdAndFriendId(userid,friendid,value);
        //return new Result(true, StatusCode.OK, "更新成功");

    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        user = userService.login(user.getMobile(), user.getPassword());
        if (user == null) {
            return new Result(false, StatusCode.ERROR, "登录失败");
        }
        String token = jwtUtil.createJWT(user.getId(),user.getMobile(),"user");
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("roles","user");

        return new Result(true, StatusCode.OK, "登录成功",map);
    }

    //发送短信验证码
    @RequestMapping(value = "/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    //注册
    @RequestMapping(value = "/register/{code}", method = RequestMethod.POST)
    public Result register(@PathVariable String code, @RequestBody User user) {
        //得到缓存中的验证码
        String redis_mobile = (String) redisTemplate.opsForValue().get(user.getMobile());
        if (redis_mobile.isEmpty()) {
            return new Result(false, StatusCode.ERROR, "请先获取验证码");
        }
        if (!redis_mobile.equals(code)) {
            return new Result(false, StatusCode.ERROR, "请输入正确的验证码");
        }
        userService.add(user);
        return new Result(true, StatusCode.OK, "注册成功");
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {

        String token_admin = (String) request.getAttribute("token_admin");
        if (token_admin == null || "".equals(token_admin)){
            return new Result(false, StatusCode.ACCESS_ERROR, "权限不足");
        }
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
