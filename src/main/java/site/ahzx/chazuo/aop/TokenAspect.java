package site.ahzx.chazuo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import site.ahzx.chazuo.util.R;
import site.ahzx.chazuo.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Aspect
@Component
public class TokenAspect {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Pointcut("@annotation(site.ahzx.chazuo.aop.TokenCheck)")
    public void tokenCheckPointcut() {}

    @Around("tokenCheckPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String bearerToken = request.getHeader("Authorization");
        log.debug("Token from header: {}", bearerToken);
        String token = jwtTokenUtil.resolveToken(bearerToken);
        if (bearerToken == null || !jwtTokenUtil.validateToken(token)) {
            log.warn("非法或过期的 token: {}", token);
            return R.fail("Token 无效或已过期");
        }

        // 如果校验通过，继续执行方法
        return joinPoint.proceed();
    }
}