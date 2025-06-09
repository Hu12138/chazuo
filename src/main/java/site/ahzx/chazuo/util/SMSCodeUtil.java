package site.ahzx.chazuo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SMSCodeUtil {

    // 存储验证码（实际项目建议用 Redis）
    private final Map<String, CodeInfo> codeMap = new ConcurrentHashMap<>();
    private static final long CODE_EXPIRE_MINUTES = 5;
    private final Random random = new Random();

    @Value("${sms.app-code}")
    private String appCode;

    @Value("${sms.template_id}")
    private String templateId;

    @Value("${sms.host}")
    private String host;

    @Value("${sms.path}")
    private String path;

    private final RestTemplate restTemplate;

    public SMSCodeUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 发送验证码
     */
    public boolean sendCode(String phone) {
        String code = generateCode();
        long expireTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(CODE_EXPIRE_MINUTES);

        // 发送短信
        boolean sendResult = sendSms(phone, code);
        if (!sendResult) {
            return false;
        }

        // 存储验证码
        codeMap.put(phone, new CodeInfo(code, expireTime));
        log.info("验证码已发送：phone={}, code={}", phone, code);
        return true;
    }

    /**
     * 发送短信验证码
     */
    private boolean sendSms(String phone, String code) {
        log.debug("phone code:{} {}", phone, code);
        String url = host + path;

        // 设置 headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "APPCODE " + appCode);

        // 设置 body 参数
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("content", "code:" + code);
        body.add("template_id", templateId);
        body.add("phone_number", phone);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // 判断状态码是否为 2xx 且响应内容包含 "OK"
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(response.getBody());
                    return "OK".equals(root.path("status").asText());
                } catch (Exception e) {
                    // 如果JSON解析失败，回退到字符串检查
                    return response.getBody().contains("\"status\":") &&
                            response.getBody().contains("OK");
                }
            }
        } catch (Exception e) {
            log.error("发送短信异常: ", e);
        }
        return false;
    }

    /**
     * 验证验证码
     */
    public boolean verifyCode(String phone, String inputCode) {
        CodeInfo codeInfo = codeMap.get(phone);
        if (codeInfo == null || !codeInfo.getCode().equals(inputCode)) {
            return false;
        }
        if (System.currentTimeMillis() > codeInfo.getExpireTime()) {
            codeMap.remove(phone); // 清理过期验证码
            return false;
        }
        return true;
    }

    /**
     * 清理验证码
     */
    public void clearCode(String phone) {
        codeMap.remove(phone);
    }

    /**
     * 生成6位随机验证码
     */
    private String generateCode() {
        return String.format("%06d", random.nextInt(999999));
    }

    /**
     * 验证码信息内部类
     */
    private static class CodeInfo {
        private final String code;
        private final long expireTime;

        public CodeInfo(String code, long expireTime) {
            this.code = code;
            this.expireTime = expireTime;
        }

        public String getCode() {
            return code;
        }

        public long getExpireTime() {
            return expireTime;
        }
    }
}