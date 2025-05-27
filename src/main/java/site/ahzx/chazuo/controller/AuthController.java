package site.ahzx.chazuo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import site.ahzx.chazuo.domain.BO.WxLogin;
import site.ahzx.chazuo.util.JwtTokenUtil;
import site.ahzx.chazuo.util.R;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;
    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthController(JwtTokenUtil jwtTokenUtil, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
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
            if (wxData == null || wxData.get("openid") == null) {
                return R.fail("获取微信用户信息失败");
            }

            String openid = (String) wxData.get("openid");
            String sessionKey = (String) wxData.get("session_key");
            String unionid = (String) wxData.getOrDefault("unionid", null);
            log.debug("openid: {}, sessionKey: {}, unionid: {}", openid, sessionKey, unionid);

            // 可进一步处理 openid，例如查询数据库或创建用户等
            String token = jwtTokenUtil.generateToken(openid, null);

            return R.ok(token);
        } catch (Exception e) {
            log.error("调用微信 jscode2session 接口失败", e);
            return R.fail("微信登录异常：" + e.getMessage());
        }
    }
}