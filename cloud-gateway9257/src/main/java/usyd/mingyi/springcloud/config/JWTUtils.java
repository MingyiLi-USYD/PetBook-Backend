package usyd.mingyi.springcloud.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtils {

    private static final String SALT = "MingyiLi";

    public static boolean verify(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SALT)).build();
            jwtVerifier.verify(token);

        } catch (Exception e) {
            throw new RuntimeException("token error");
        }
        return true;
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


}
