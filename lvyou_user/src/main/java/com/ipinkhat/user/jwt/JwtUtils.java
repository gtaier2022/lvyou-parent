package com.ipinkhat.user.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Map;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/4/26-12:39
 */
@Data
@ConfigurationProperties("jwt.config")
@Configuration
public class JwtUtils {
private String key;
private Long ttl;

    /**
     * 创建token
     * @param id
     * @param subject
     * @param map
     * @return
     */
    public String createJWT(String id, String subject, Map<String,Object> map){
        long now = System.currentTimeMillis();
        long exp = now+ttl;
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).
                setSubject(subject).
                setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,key);
        if (map!=null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                jwtBuilder.claim(entry.getKey(), entry.getValue());
            }
        }
        if (ttl>0){
            jwtBuilder.setExpiration(new Date(exp));
        }
        return jwtBuilder.compact();

    }

    public Claims parseJWT(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (Exception e) {
        }
        return claims;


    }
}
