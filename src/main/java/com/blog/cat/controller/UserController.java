package com.blog.cat.controller;

import com.blog.cat.annotation.NormalToken;
import com.blog.cat.annotation.PassToken;
import com.blog.cat.common.exception.CommonExceptionEnum;
import com.blog.cat.common.exception.UserException;
import com.blog.cat.common.type.CommonReturnType;
import com.blog.cat.controller.view.UserView;
import com.blog.cat.dao.UserDao;
import com.blog.cat.entity.User;
import com.blog.cat.service.UserService;
import com.blog.cat.util.EmailUtil;
import com.blog.cat.util.RedisUtil;
import com.blog.cat.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailUtil emailUtil;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private static Pattern USERNAME_PATTERN = Pattern.compile("^[a-z]\\d{6,13}");

    private static Pattern PWD_PATTERN = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");

    private static Pattern NICKNAME_PATTERN = Pattern.compile("\\s+");

    private static Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");


    @PassToken
    @PostMapping("/login")
    public CommonReturnType login(@RequestBody User user) throws UserException {
        User result = userService.login(user.getUid(), user.getPwd());
        String token = tokenUtil.getToken(user);
        redisUtil.setRedisWithTimeOut("token", result.getUid(), token, 1000 * 60 * 60 * 3);
        Map<String, String> reData = new HashMap<>(2);
        reData.put("token", token);
        return new CommonReturnType(reData);
    }

    @PassToken
    @PostMapping("/register")
    public CommonReturnType register(@RequestBody UserView view) throws Exception {
        String verifyCode = view.getVerifyCode();
        String email = view.getEmail();
        String REDIS_KEY = "email";
        if ( !redisUtil.getKey(REDIS_KEY, email).equals(verifyCode)) {
            throw new UserException(CommonExceptionEnum.EMAIL_CODE_ERR);
        }
        Integer result = userService.register(userViewToUser(view));
        CommonReturnType re = new CommonReturnType();
        if (result == null || result == 0) {
            re.setCode(999);
            re.setData("注册失败");
            re.setMsg("fail");
            return re;
        }
        re.setCode(0);
        re.setMsg("success");
        re.setData("注册成功");
        return re;
    }

    @PassToken
    @GetMapping("/isUidExist")
    public CommonReturnType isUidExist(@RequestParam("uid") String uid) throws UserException {
        Matcher matcher = USERNAME_PATTERN.matcher(uid);
        if (!matcher.find()) {
            throw new UserException(CommonExceptionEnum.USER_NAME_NOT_ILL);
        }
        userService.isUidExist(uid);
        return new CommonReturnType("未被注册可以使用");
    }


    @PassToken
    @GetMapping("/isPwdLegal")
    public CommonReturnType isPwdLegal(@RequestParam("pwd") String pwd) {
        Matcher matcher = PWD_PATTERN.matcher(pwd);
        if (!matcher.find()) {
            return new CommonReturnType(999, "fail", "密码格式有错");
        }

        return new CommonReturnType("密码可以正确使用");
    }


    @PassToken
    @GetMapping("/isNicknameLegal")
    public CommonReturnType isNicknameLegal(@RequestParam("nickname") String nickname) throws UserException {
        if ("".equals(nickname.trim())) {
            return new CommonReturnType(999, "fail", "不能为空!");
        }
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);
        if (matcher.find()) {
            return new CommonReturnType(999, "fail", "不能有空格!");
        }
        userService.isNicknameExist(nickname);
        return new CommonReturnType("可以使用");
    }

    @PassToken
    @GetMapping("/isEmailLegal")
    public CommonReturnType isEmailLegal(@RequestParam("email") String email) throws UserException {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.find()) {
            return new CommonReturnType(999, "fail", "邮箱格式错误");
        }
        userService.isEmailExist(email);

        return new CommonReturnType("可以使用");
    }

    @PassToken
    @GetMapping("/isPhoneLegal")
    public CommonReturnType isPhoneLegal(@RequestParam("phone") String phone) throws UserException {
        if(phone == null || "".equals(phone)){
            return new CommonReturnType(999, "fail", "手机号码错误");
        }
        Long phoneNum = Long.parseLong(phone);
        userService.isPhoneExist(phoneNum);
        return new CommonReturnType("可以使用");
    }

    @PassToken
    @GetMapping("/getVerifyCode")
    public CommonReturnType getEmailVerifyCode(@RequestParam String email) throws UserException {
        Random r = new Random();
        int random = r.nextInt(999999);
        logger.info(email);
        String verifyCode = random > 100000 ? String.valueOf(random) : String.valueOf(random + 100000);
        redisUtil.setRedisWithTimeOut("email", email, verifyCode, 1000 * 60 * 15);
        try {
            emailUtil.sendMail(email, verifyCode);
        } catch (Exception e) {
            redisUtil.deleteKey("email", email);
            throw new UserException(CommonExceptionEnum.SEND_VERIFY_MAIL_FAIL);
        }
        return new CommonReturnType("发送成功");
    }


    @PassToken
    @GetMapping("/isTokenLegal")
    public CommonReturnType isTokenLegal(@RequestParam String token) throws Exception {
        tokenUtil.handlerToken(token,userDao,redisUtil);
        return new CommonReturnType("验证通过");
    }


    @NormalToken
    @PostMapping("/uploadHeadPic")
    public CommonReturnType uploadHeadPic(@RequestBody MultipartFile file){
        return new CommonReturnType("上传成功");
    }


    public User userViewToUser(UserView src) {
        if (src == null) {
            return null;
        }
        User dest = new User();
        BeanUtils.copyProperties(src, dest);
        return dest;
    }

}
