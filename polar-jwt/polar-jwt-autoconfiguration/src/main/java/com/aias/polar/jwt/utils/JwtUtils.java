package com.aias.polar.jwt.utils;

import com.aias.polar.jwt.porperties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * Jwt工具类
 *
 * @author Leo
 * @date 2018/3/28
 */
public class JwtUtils {

//    private String secret;

//    private long expiration;

//    private String signatureAlgorithmName;

    @Resource
    private JwtProperties properties;

//    @PostConstruct
//    public void init() {
//        this.secret = this.properties.getSecret();
//        this.expiration = this.properties.getExpiration();
//        this.signatureAlgorithmName = this.properties.getSignatureAlgorithmName();
//    }

    /**
     * 生成加密key
     *
     * @return
     */
    private SecretKey generateKey() {
        byte[] encodedKey = Base64.getDecoder().decode(properties.getSecret());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     *
     * @param subject
     * @return
     */
    public String createJWT(String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(properties.getSignatureAlgorithmName());
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generateKey();
        JwtBuilder builder = Jwts.builder().setId("jwt").setIssuedAt(now).setSubject(subject)
                                 .signWith(signatureAlgorithm, key);
        if (properties.getExpiration() >= 0) {
            long expMillis = nowMillis + properties.getExpiration();
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) {
        SecretKey key = generateKey();
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 从jwt中获取subject信息
     *
     * @param jwt
     * @param key
     * @return
     */
    public String getSubjectFromJwt(String jwt, String key) {
        Claims claims = this.parseJWT(jwt);
        String subject = claims.getSubject();
//        if (StringUtils.isNotEmpty(key)) {
//            JSONObject json = JSONObject.parseObject(subject);
//            subject = json.getString(key);
//        }
        return subject;

    }

    public String getSubjectFromJwt(String jwt) {
        return getSubjectFromJwt(jwt, null);

    }

    /**
     * 验证jwt
     *
     * @param jwt
     * @return
     */
    public boolean verifyJwt(String jwt) {
        try {
            this.parseJWT(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
