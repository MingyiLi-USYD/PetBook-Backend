package usyd.mingyi.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.pojo.User;


import java.util.Calendar;

public class JWTUtils {

    private static final String SALT = "MingyiLi";

    public static String generateToken(User user) {
        Calendar instance = Calendar.getInstance();
        //默认令牌过期时间7天
        instance.add(Calendar.DATE, 90);

        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("userId", user.getUserId())
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole());

        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SALT));
        return token;
    }

    public static boolean verify(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SALT)).build();
            DecodedJWT verify = jwtVerifier.verify(token);

        } catch (Exception e) {
            throw new CustomException("token error");
        }
        return true;
    }

    public static boolean verifyInSocket(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SALT)).build();
            DecodedJWT verify = jwtVerifier.verify(token);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static DecodedJWT getTokenInfo(String token) {
        return JWT.require(Algorithm.HMAC256(SALT)).build().verify(token);
    }

    public static String getUsername(String token) {
        return JWT.require(Algorithm.HMAC256(SALT)).build().verify(token).getClaim("username").asString();
    }

    public static Long getUserId(String token) {
        return JWT.require(Algorithm.HMAC256(SALT)).build().verify(token).getClaim("userId").asLong();
    }

    public static String getRole(String token) {
        return JWT.require(Algorithm.HMAC256(SALT)).build().verify(token).getClaim("role").asString();
    }

    public static HttpHeaders extraHeader(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SALT)).build().verify(token);
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("X-Username", verify.getClaim("username").asString());
        httpHeaders.add("X-UserId", verify.getClaim("userId").asString());
        httpHeaders.add("X-role", verify.getClaim("role").asString());
        return httpHeaders;
    }


}
