package com.pcp.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @depict: 创建Token
 * @author: PCP
 * @create: 2019-03-11 13:39
 */
public class CreateJwtTest {

    public static void main(String[] args) {

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666")
                .setSubject("小妹")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 60 * 1000))
                .signWith(SignatureAlgorithm.HS256,"itpcp")
                .claim("role","pcp哈哈");
        System.out.println(jwtBuilder.compact());
    }
}
