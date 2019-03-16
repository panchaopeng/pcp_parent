package com.pcp.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @depict: 前台网关过滤器。经过网关的头信息会丢失，所以需要对头信息进行转发
 * @author: PCP
 * @create: 2019-03-14 21:17
 */
@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("web网关启动了");
        //得到request上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //得到request的域
        HttpServletRequest request = requestContext.getRequest();
        //得到Token的头信息
        String token = request.getHeader("token");
        //判断是否有头信息，有的话则让zuul网关转发
        if (token != null && !"".equals(token)){
            requestContext.addZuulRequestHeader("token",token);

        }
        return null;
    }
}
