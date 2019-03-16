package com.pcp.recruit.controller;
import com.pcp.recruit.pojo.Recruit;
import com.pcp.recruit.service.RecruitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * recruit控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {

	@Autowired
	private RecruitService recruitService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",recruitService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",recruitService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",recruitService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param recruit
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Recruit recruit  ){
		recruitService.add(recruit);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param recruit
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Recruit recruit, @PathVariable String id ){
		recruit.setId(id);
		recruitService.update(recruit);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id){
		recruitService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * @depict: 最新6条记录职位
	 * @return: entity.Result {@link Result#}
	 * @author: PCP
	 * @create: 2019/2/25 17:14
	 */
    @RequestMapping(value="/search/recommend",method= RequestMethod.GET)
	public Result recommendRecruit(){
	    return new Result(true,StatusCode.OK,"最新推荐职位",recruitService.recommendRecruit());
    }

    /**
     * @depict: 最新6条职位
     * @return: entity.Result {@link entity.Result#}
     * @author: PCP
     * @create: 2019/2/25 17:15
     */
    @RequestMapping(value="/search/latest",method= RequestMethod.GET)
    public Result lastedRecruit(){
        return new Result(true,StatusCode.OK,"最新职位",recruitService.latestRecruit());
    }
	
}
