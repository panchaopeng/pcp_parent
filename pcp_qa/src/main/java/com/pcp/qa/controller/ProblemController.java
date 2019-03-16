package com.pcp.qa.controller;

import com.pcp.qa.client.BaseClient;
import com.pcp.qa.pojo.Problem;
import com.pcp.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * problem控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private HttpServletRequest request;

	//爆红是因为在spring容器中找不到该接口的实现类。
	// 而我们不需要该接口的实现类，因为这是模块间的调用
	//强迫症可以在接口加注解 @Component
	@Autowired
	private BaseClient baseClient;
	@RequestMapping(value = "/label/{labelId}",method = RequestMethod.GET)
	Result findLableById(@PathVariable("labelId") String labelId){
		return baseClient.findById(labelId);
	}
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
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
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		String token_user = (String) request.getAttribute("token_user");
		if (token_user == null || "".equals(token_user)){
			return new Result(false,StatusCode.ACCESS_ERROR,"权限不足");

		}
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

    //最新回答
    @RequestMapping(value="/latest/{labelId}/{page}/{size}",method= RequestMethod.GET)
    public Result latest(@PathVariable String labelId,
                         @PathVariable int page,
                         @PathVariable int size){
        Page<Problem> pageData = problemService.latest(labelId, page, size);
        return new Result(true,StatusCode.OK,"最新回答",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    //最热回答
    @RequestMapping(value="/hotList/{labelId}/{page}/{size}",method= RequestMethod.GET)
    public Result hotList(@PathVariable String labelId,
                         @PathVariable int page,
                         @PathVariable int size){
        Page<Problem> pageData = problemService.hotList(labelId, page, size);
        return new Result(true,StatusCode.OK,"最热回答",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    //等待回答
    @RequestMapping(value="/waitList/{labelId}/{page}/{size}",method= RequestMethod.GET)
    public Result waitList(@PathVariable String labelId,
                         @PathVariable int page,
                         @PathVariable int size){
        Page<Problem> pageData = problemService.waitList(labelId, page, size);
        return new Result(true,StatusCode.OK,"等待回答",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }
	
}
