package com.pcp.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * @depict: 解析客户端传过来的Token
 * @author: PCP
 * @create: 2019-03-11 14:15
 */
public class ParseJwtTest {

    public static void main(String[] args) {
        Claims claims = Jwts.parser()
                .setSigningKey("itpcp")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_lprkiLCJpYXQiOjE1NTIyODU4MjUsImV4cCI6MTU1MjI4NTg4NSwicm9sZSI6InBjcOWTiOWTiCJ9.8hylh_NK8GS6ifUmHx3JU-UXL7Rkd6p19cjILQVy-78")
                .getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("name:" + claims.getSubject());
        System.out.println("issued:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("expiration:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("role:"+ claims.get("role"));
    }
}
