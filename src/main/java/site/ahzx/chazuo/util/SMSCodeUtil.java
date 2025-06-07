package site.ahzx.chazuo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class SMSCodeUtil {

    private static final Logger log = LoggerFactory.getLogger(SMSCodeUtil.class);
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
     * 发送短信验证码
     *
     * @param phone 手机号
     * @param code  验证码内容
     * @return true: 发送成功，false: 发送失败
     */
    public boolean sendCode(String phone, String code) {
        log.debug("phone code:{} {}", phone,code);
        String url = host + path;

        // 设置 headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "APPCODE " + appCode);

        // 设置 body 参数
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("content", "code:"+code);
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
            else {
                return false;
            }

        } catch (Exception e) {
            System.err.println("发送短信异常: " + e.getMessage());
        }

        return false;
    }
}