package com.pcp.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @depict: zuul 后台网关过滤器
 * @author: PCP
 * @create: 2019-03-14 21:02
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    //在请求前pre或者后post执行
    @Override
    public String filterType() {
        return "pre";
    }

    //多个过滤器执行的优先级
    //越小表示优先级越高
    @Override
    public int filterOrder() {
        return 0;
    }

    //是否开启该过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //过滤器执行的操作，return 任意的Object值都表示继续执行
    //setSendZuulResponse(false) 表示不再继续执行
    @Override
    public Object run() throws ZuulException {
        System.out.println("后台网关过滤器启动了");
        //得到request上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //得到request的域
        HttpServletRequest request = requestContext.getRequest();

        //网关要分发请求，也即要对OPTIONS操作进行放行
        if ("OPTIONS".equals(request.getMethod())){
            return null;
        }

        //如果请求时登录地址，则放行
        if (request.getRequestURI().indexOf("login") > 0){
            return null;
        }

        //得到Token的头信息
        String header = request.getHeader("token");
        //判断是否有头信息，有的话则让zuul网关转发
        if (header != null && !"".equals(header)){
            if (header.startsWith("Bearer_")){

                String token = header.substring(7);
                //对令牌进行验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    //这是管理员
                    if (claims != null && "admin".equals(roles)) {
                        //将Token转发
                        requestContext.addZuulRequestHeader("token",token);
                        return null;
                    }
                    //这是普通用户
                    if (claims != null && "user".equals(roles)) {
                        //将Token分发
                        requestContext.addZuulRequestHeader("token",token);
                        return null;
                    }
                } catch (Exception e) {
                    //如果令牌不正确，说明是 瞎写的/伪造的/过期的
                    //终止运行
                    requestContext.setSendZuulResponse(false);

                }
            }

        }
        //终止运行
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(401);
        requestContext.setResponseBody("权限不足");
        //设置编码
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
