package com.pcp.user.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @depict: JWT拦截器对象，用来验证Token
 * @author: PCP
 * @create: 2019-03-11 20:13
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //System.out.println("jwt拦截器");
        //无论如何都放行。具体能不能操作还是在具体的操作中去判断
        //拦截器只是负责把请求头中包含的Token令牌进行一个解析验证
        //拦截的请求中，不一定非得携带Token

        //1.得到携带Token的请求头
        //String header = request.getHeader("Authorization");
        String header = request.getHeader("token");
        //2.判断是否存在该请求头，如果有就解析。没有就不理会。因为在所有的请求中，不一定都携带Token
        if (header !=null && !"".equals(header)){
            if (header.startsWith("Bearer_")){
                //3.得到Token
                String token = header.substring(7);
                //4.对令牌进行验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    //这是管理员
                    if (claims != null && "admin".equals(roles)) {
                        request.setAttribute("token_admin",token);
                    }
                    //这是普通用户
                    if (claims != null && "user".equals(roles)) {
                        request.setAttribute("token_user",token);
                    }
                } catch (Exception e) {
                    //如果令牌不正确，说明是 瞎写的/伪造的/过期的
                    throw new RuntimeException("令牌不正确");

                }
            }
        }
        return true;
    }
}
