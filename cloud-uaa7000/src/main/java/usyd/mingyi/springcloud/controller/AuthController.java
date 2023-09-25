package usyd.mingyi.springcloud.controller;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import usyd.mingyi.springcloud.common.CustomException;

import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.config.GoogleTokenInfoResponse;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.UserService;
import usyd.mingyi.springcloud.utils.JWTUtils;
import usyd.mingyi.springcloud.utils.PasswordUtils;

import java.util.Random;
import java.util.UUID;

@RestController
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    RestTemplate restTemplate;



    @PostMapping("/oauth/login")
    public R<String> userLogin(@RequestParam("username")String username,String password){
        User user =  userService.getUserByUsername(username);
        if (!PasswordUtils.verifyPassword(password,user.getPassword())) {
            throw new CustomException("账号或者密码错误");
        }
        String token = JWTUtils.generateToken(user);
        return R.success(token);
    }

    @PostMapping("/oauth/login/thirdPart")
    public R<String> userLogin(@RequestParam("googleToken")String googleToken){

        String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + googleToken;
        try{
            GoogleTokenInfoResponse response = restTemplate.getForObject(url, GoogleTokenInfoResponse.class);

            User user =  userService.getUserByUsername(response.getEmail());
            if (user==null){
                //证明第一次登录 还没注册 先注册
                User newUser = new User();
                String tmpPassword = RandomUtil.randomString(10);
                newUser.setUsername(response.getEmail());
                newUser.setAvatar(response.getPicture());
                newUser.setNickname(response.getName());
                newUser.setPassword(tmpPassword);
                newUser.setRole("User");
                newUser.setUuid(UUID.randomUUID().toString());
                newUser.setStatus((byte)1);
                if (userService.save(newUser)) {
                    String token = JWTUtils.generateToken(newUser);
                    return R.success(token);
                }
            }else {
                String token = JWTUtils.generateToken(user);
                return R.success(token);
            }

        }catch (HttpClientErrorException e){
            throw new CustomException("invalid token");
        }

        throw new CustomException("System error");

    }

}
