package site.ahzx.chazuo.util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        // 你可以在这里进行各种配置，例如：
        // restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        // restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        return new RestTemplate();
    }
}