package usyd.mingyi.springcloud.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class JwtUtils {
    // 定义签名密钥
    private static final String SECRET_KEY = "MingyiLi";

    // 验证 JWT
    public static boolean validateJwt(String jwt) {
        try {
            // 创建签名密钥
            Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            // 验证 JWT
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt);

            // 如果没有抛出异常，表示验证成功
            return true;
        } catch (Exception e) {
            // 验证失败
            return false;
        }
    }

    // 提取 user_name 信息
    public static String extractUserName(String jwt) {
        try {
            // 创建签名密钥
            Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            // 解析 JWT
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt);

            // 提取 username
            Claims body = claimsJws.getBody();
            return body.get("username", String.class);
        } catch (Exception e) {
            // 提取失败，返回 null 或抛出异常，根据需要处理
            return null;
        }
    }

    // 提取 userId 信息
    public static String extractUserId(String jwt) {
        try {
            // 创建签名密钥
            Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            // 解析 JWT
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt);

            // 提取 userId
            Claims body = claimsJws.getBody();
            return body.get("userId", String.class);
        } catch (Exception e) {
            // 提取失败，返回 null 或抛出异常，根据需要处理
            return null;
        }
    }
}
