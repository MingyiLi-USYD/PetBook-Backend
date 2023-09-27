package usyd.mingyi.springcloud.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.config.GoogleTokenInfoResponse;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.UserService;
import usyd.mingyi.springcloud.utils.JWTUtils;
import usyd.mingyi.springcloud.utils.PasswordUtils;

import javax.validation.constraints.Email;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate<String, String> redisTemplate;


    @PostMapping("/oauth/login/password")
    public R<String> userPasswordLogin(@Email @RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.getUserByUsername(username);
        if (!PasswordUtils.verifyPassword(password, user.getPassword())) {
            throw new CustomException("账号或者密码错误");
        }
        String token = JWTUtils.generateToken(user);
        return R.success(token);
    }

    @PostMapping("/oauth/login/code")
    public R<String> userCodeLogin(@Email @RequestParam("email") String username, @RequestParam("code") String code) {


        if (!redisTemplate.hasKey(username)) {
            throw new CustomException("No code found");
        }
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String redisCode = operations.getAndDelete(username);
        if (redisCode != null && redisCode.equals(code)) {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                //证明第一次登录 还没注册 先注册
                User newUser = new User();
                String tmpPassword = RandomUtil.randomString(10);
                newUser.setUsername(username);
                newUser.setAvatar("https://source.unsplash.com/random/}"+RandomUtil.randomNumbers(3));
                newUser.setNickname("User"+RandomUtil.randomNumbers(5));
                newUser.setPassword(PasswordUtils.hashPassword(tmpPassword));
                newUser.setRole("User");
                newUser.setUuid(UUID.randomUUID(true).toString());
                newUser.setStatus((byte) 1);
                if (userService.save(newUser)) {
                    userService.sendTempPassword(username,tmpPassword);
                    String token = JWTUtils.generateToken(newUser);
                    return R.success(token);
                }
            }
        }else {
            return R.error("code not equal");
        }
            return R.error("Fail to login");

    }

    @PostMapping("/oauth/login/thirdPart")
    public R<String> userLogin(@RequestParam("googleToken") String googleToken) {

        String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + googleToken;
        try {
            GoogleTokenInfoResponse response = restTemplate.getForObject(url, GoogleTokenInfoResponse.class);

            User user = userService.getUserByUsername(response.getEmail());
            if (user == null) {
                //证明第一次登录 还没注册 先注册
                User newUser = new User();
                String tmpPassword = RandomUtil.randomString(10);
                newUser.setUsername(response.getEmail());
                newUser.setAvatar(response.getPicture());
                newUser.setNickname(response.getName());
                newUser.setPassword(PasswordUtils.hashPassword(tmpPassword));
                newUser.setRole("User");
                newUser.setUuid(UUID.randomUUID(true).toString());
                newUser.setStatus((byte) 1);
                if (userService.save(newUser)) {
                    userService.sendTempPassword(response.getEmail(),tmpPassword);
                    String token = JWTUtils.generateToken(newUser);
                    return R.success(token);
                }
            } else {
                String token = JWTUtils.generateToken(user);
                return R.success(token);
            }

        } catch (HttpClientErrorException e) {
            throw new CustomException("invalid token");
        }

        throw new CustomException("System error");

    }


    @GetMapping("/oauth/code/{email}")
    public R<String> sendEmailCode(@PathVariable("email") @Email String email) {
        String code = RandomUtil.randomStringUpper(6);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(email, code, 5, TimeUnit.MINUTES);
        userService.sendEmail(email, code);
        return R.success("Success to send code");
    }


}
