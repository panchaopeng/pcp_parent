package com.pcp.base.controller;

import com.pcp.base.pojo.Label;
import com.pcp.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @depict: Controller编写基本的CRUD操作
 * @author: PCP
 * @create: 2019-02-21 22:56
 */

@RestController // 相当于 @Controller和@ResponseBody，直接将返回对象转换为json类型
@CrossOrigin // 跨域，前后端分离开发与部署，必然存在跨域问题
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private HttpServletRequest request;

    /**
     * @depict: 查询全部数据
     * @return: entity.Result {@link Result}
     * @author: PCP
     * @create: 2019/2/21 23:10
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        System.out.println(request.getHeader("token"));
        return new Result(true,StatusCode.OK,"查询成功",labelService.findAll());
    }

    /**
     * @depict: 根据 id 查询数据
     * @param1: labelId
     * @return: entity.Result {@link Result}
     * @author: PCP
     * @create: 2019/2/21 23:10
     */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId){
        return new Result(true,StatusCode.OK,"根据id查询成功",labelService.findById(labelId));
    }

    /**
     * @depict: 获得请求传送过来的Label类数据，并存储
     * @param1: label
     * @return: entity.Result {@link Result}
     * @author: PCP
     * @create: 2019/2/21 23:20
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    /**
     * @depict: 根据 id 更新Label数据
     * @param1: labelId
     * @param2: label {@link Label}
     * @return: entity.Result {@link Result}
     * @author: PCP
     * @create: 2019/2/21 23:23
     */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable("labelId") String labelId,@RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"根据id更新成功");
    }

    /**
     * @depict: 根据 id 删除Label数据
     * @param1: labelId
     * @return: entity.Result {@link Result#}
     * @author: PCP
     * @create: 2019/2/21 23:29
     */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("labelId") String labelId){
        labelService.deleteById(labelId);
        return new Result(true,StatusCode.OK,"根据id删除成功");
    }

    /**
     * @depict: @Query注解测试
     * @param1: name {@link Label#labelname}
     * @param2: labelId  {@link Label#id}
     * @return: entity.Result {@link Result#}
     * @author: PCP
     * @create: 2019/2/22 23:04
     */
    @RequestMapping(value = "/{name}/{labelId}",method = RequestMethod.PUT)
    public Result updateName(@PathVariable("name") String name,
                             @PathVariable("labelId") String labelId){
        labelService.updateName(name,labelId);
        return new Result(true,StatusCode.OK,"updateName",labelService.findById(labelId));
    }

    /**
     * @depict: 根据Label对象查找所有Label
     * @param1: label
     * @return: entity.Result {@link Result#}
     * @author: PCP
     * @create: 2019/2/25 11:05
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        return new Result(true,StatusCode.OK,"search成功",labelService.findSearch(label));
    }

    /**
     * @depict: （分页集合）根据页码和页大小查找
     * @param1: label
     * @param2: page
     * @param3: size
     * @return: entity.Result {@link Result#}
     * @author: PCP
     * @create: 2019/2/25 11:05
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Label label,
                            @PathVariable("page") int page,
                            @PathVariable("size") int size){
        Page<Label> labelPage = labelService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"pageQuery成功",new PageResult<Label>(labelPage.getTotalElements(),labelPage.getContent()));
    }
}
