package usyd.mingyi.springcloud.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.springcloud.mapper.UserMapper;



@Service
@Slf4j
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private JavaMailSender javaMailSender;



    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,username))  ;
    }

    @Override
    @Async
    public void sendEmail(String to,String code) {
        String subject = "Petbook Verification Code Email";
        String body = "Your verification code is: " + code;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    @Override
    @Async
    public void sendTempPassword(String to, String password) {
        String subject = "Welcome to Petbook";
        String body = generateWelcomeEmailBody(password);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    private String generateWelcomeEmailBody(String password) {
        // 构建欢迎邮件模板
        String template = "Subject: Welcome to Petbook\n\n"
                + "Dear user,\n\n"
                + "Thank you for registering with Petbook. Welcome to our community!\n\n"
                + "At Petbook, you can enjoy the following features:\n"
                + "- Social Interaction: Share your stories and photos with fellow pet enthusiasts.\n"
                + "- Find New Friends: Browse and connect with users who share your interests.\n"
                + "- Manage Your Pet Profiles: Keep track of your pets' lives and health information.\n\n"
                + "Your initial password is: [Initial Password]. Please keep this password secure and change it to your own secure password immediately after your first login. You can find the password change option on the login page.\n\n"
                + "If you have any questions or need assistance, please feel free to contact our customer support team.\n\n"
                + "Once again, thank you for joining the Petbook community! We look forward to sharing more joyful moments with you and your pets.\n\n"
                + "Best regards,\n\n"
                + "The Petbook Team";

        // 替换模板中的变量
        template = template.replace("[Initial Password]", password);

        return template;
    }
}
