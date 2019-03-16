package com.pcp.spit.controller;

import com.pcp.spit.pojo.Spit;
import com.pcp.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @depict: 吐槽控制类
 * @author: PCP
 * @create: 2019-02-26 14:51
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/findall",method = RequestMethod.GET)
    public Result fiindAll(){
        return new Result(true, StatusCode.OK,"spit find all",spitService.findAll());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK,"spit findById",spitService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK,"spit save");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Spit spit){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK,"spit update");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK,"spit delete");
    }

    @RequestMapping(value = "/comment/{parentId}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentId,
                                 @PathVariable int page,
                                 @PathVariable int size){
       Page<Spit> spitPage = spitService.findByParentid(parentId,page,size);
       return new Result(true,StatusCode.OK,"吐槽分页",new PageResult<Spit>(spitPage.getTotalElements(),spitPage.getContent()));
    }

    //Mongodb吐槽效率优化
    //Redis控制不能重复点赞
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){
        //如果点赞标识不为空
        if (redisTemplate.opsForValue().get("thumbup_"+ spitId) != null){
            return new Result(false,StatusCode.REPEAT_ERROR,"不能重复点赞");
        }
        spitService.addThumbup(spitId);
        //否则
        redisTemplate.opsForValue().set("thumbup_"+ spitId,1);
        return new Result(true,StatusCode.OK,"点赞成功");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public void delete(){
        spitService.deleteAll();
    }

}
