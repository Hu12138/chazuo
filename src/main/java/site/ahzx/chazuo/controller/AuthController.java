package site.ahzx.chazuo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import site.ahzx.chazuo.aop.UserContext;
import site.ahzx.chazuo.domain.BO.LoginBO;
import site.ahzx.chazuo.domain.BO.RegisterBO;
import site.ahzx.chazuo.domain.BO.SendCodeBO;
import site.ahzx.chazuo.domain.BO.WxLogin;
import site.ahzx.chazuo.domain.PO.UserPO;
import site.ahzx.chazuo.domain.VO.LoginVO;
import site.ahzx.chazuo.domain.VO.WxLoginVO;
import site.ahzx.chazuo.mapper.UserMapper;
import site.ahzx.chazuo.service.UserService;
import site.ahzx.chazuo.domain.CodeInfo;
import site.ahzx.chazuo.util.JwtTokenUtil;
import site.ahzx.chazuo.util.R;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    // 模拟验证码存储，key:手机号，value:验证码和过期时间
    private static final Map<String, CodeInfo> codeMap = new HashMap<>();
    private static final long CODE_EXPIRE_MINUTES = 5;
    private static final Random random = new Random();

    private final JwtTokenUtil jwtTokenUtil;
    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    private  UserService userService ;
    @Autowired
    private ConfigurationPropertiesAutoConfiguration configurationPropertiesAutoConfiguration;
    @Autowired
    private UserContext userContext;
    @Autowired
    public AuthController(JwtTokenUtil jwtTokenUtil, RestTemplate restTemplate, ObjectMapper objectMapper, UserMapper userMapper) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

    }

    @PostMapping("/secure-api")
    public R secureApi() {

        log.debug("openid is {}", userContext.getCurrentUser());
        return R.ok("你通过了 token 校验");
    }
    @PostMapping("/wxlogin")
    public R wxlogin(@RequestBody @Validated WxLogin wxLogin) {
        String code = wxLogin.getCode();
        log.debug("wxLogin code is: {}", code);

        // 拼接请求 URL
        String wxUrl = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appId, appSecret, code
        );
        log.debug("wxUrl is: {}", wxUrl);

        try {
            // 获取原始 JSON 字符串
            ResponseEntity<String> response = restTemplate.getForEntity(wxUrl, String.class);
            String body = response.getBody();
            log.debug("wx response body: {}", body);

            Map<String, Object> wxData = objectMapper.readValue(body, Map.class);
            log.debug("wxData: {}", wxData);
//            if (wxData == null || wxData.get("openid") == null) {
//                return R.fail("获取微信用户信息失败");
//            }

            String openid = (String) wxData.get("openid");
            String sessionKey = (String) wxData.get("session_key");
            String unionid = (String) wxData.getOrDefault("unionid", null);
            log.debug("openid: {}, sessionKey: {}, unionid: {}", openid, sessionKey, unionid);

                    // 可进一步处理 openid，例如查询数据库或创建用户等
            openid = "test";
            // 微信登录写死角色为普通用户
            String role = "common";
            String token = jwtTokenUtil.generateToken(openid, Collections.singletonList(role));
            WxLoginVO  wxLoginVO = new WxLoginVO();
            wxLoginVO.setToken(token);
            wxLoginVO.setOpenid(openid);
            wxLoginVO.setRole(role);


            Integer cnt = userService.countOpenId(openid);
            if (cnt == 0){
                UserPO userPO  = new UserPO();
                userPO.setOpenid(openid);
                userService.insertUser(userPO);
            }

            return R.ok("登录成功", wxLoginVO);
        } catch (Exception e) {
            log.error("调用微信 jscode2session 接口失败", e);
            return R.fail("微信登录异常：" + e.getMessage());
        }
    }

    @PostMapping("/sendCode")
    public R sendCode(@RequestBody SendCodeBO sendCodeBO) {
        String phone = sendCodeBO.getPhone();
        log.debug("phone is {}", phone);
        // 模拟发送验证码
        String code = String.format("%06d", random.nextInt(999999));
        long expireTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(CODE_EXPIRE_MINUTES);

        codeMap.put(phone, new CodeInfo(code, expireTime));
        log.info("模拟发送验证码：手机号={}, 验证码={} ", phone, code);
        return R.ok("验证码已发送");
    }

    @PostMapping("/register")
    public R register(@Validated @RequestBody RegisterBO registerBO) {
        String phone = registerBO.getPhone();
        String code = registerBO.getCode();
        String password = registerBO.getPassword();
        log.debug("phone is {}", phone);
        // 验证验证码
        CodeInfo codeInfo = codeMap.get(phone);
        log.debug("codeInfo: {}", codeInfo);
        if (codeInfo == null || !codeInfo.getCode().equals(code)) {
            return R.fail("验证码错误或已过期");
        }
        if (System.currentTimeMillis() > codeInfo.getExpireTime()) {
            codeMap.remove(phone);
            return R.fail("验证码已过期");
        }

        // 检查用户是否已存在
        UserPO user = userService.getUserByPhone(phone);
        if (user != null) {
            return R.fail("该手机号已注册");
        }

        // 创建新用户
        user = new UserPO();
        user.setPhone(phone);
        user.setPassword(password);
        userService.insertUser(user);


        // 生成token
        String role = "admin"; // 注册用户默认为普通用户
        String token = jwtTokenUtil.generateToken(phone, Collections.singletonList(role));

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setRole(role);
        loginVO.setPhone(phone);

        // 清除已使用的验证码
        codeMap.remove(phone);
        return R.ok("注册成功", loginVO);
    }

    @PostMapping("/login")
    public R login(@RequestBody @Validated LoginBO login) {
        // 1. 根据手机号密码查找用户
        UserPO user = userService.getUserByPhone(login.getPhone());
        if (user == null) {
            return R.fail("用户名不存在");
        }
        if (!user.getPassword().equals(login.getPassword())) {
            return R.fail("密码错误");
        }
        // 2.根据用户找到角色
//        String role = userService.getRoleByUserId(Long.valueOf(user.getId()));
        // 手机号登录的通通写死成管理员账户
        String role = "admin";

        // 3.生成 token
        String token = jwtTokenUtil.generateToken(user.getPhone(), Collections.singletonList(role));

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setRole(role);
        loginVO.setPhone(user.getPhone());
        return R.ok("登录成功", loginVO);
    }
}
