package com.zy.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "zy";

	//接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
//                载荷
                .withClaim("claims", claims)
//                过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
//                选择加密算法和密钥
                .sign(Algorithm.HMAC256(KEY));
    }

	//接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
//        解密
        return JWT.require(Algorithm.HMAC256(KEY))
//                生成验证器
                .build()
//                解析token
                .verify(token)
//                获取解析以后的JWT对象
                .getClaim("claims")
//                转换为Map
                .asMap();
    }

}
