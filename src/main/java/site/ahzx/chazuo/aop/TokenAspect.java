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
    @Autowired
    private UserContext userContext;


    // 定义排除的路径
    private static final String[] EXCLUDED_PATHS = {
            "/auth/wxlogin",
            "/auth/register"
    };

    // 主切点：匹配所有Controller方法
    @Pointcut("execution(* site.ahzx.chazuo.controller..*.*(..))")
    public void controllerPointcut() {}

    // 排除切点：匹配排除的路径
    @Pointcut("execution(* site.ahzx.chazuo.controller.AuthController.wxlogin(..)) || " +
            "execution(* site.ahzx.chazuo.controller.AuthController.register(..))")
    public void excludedPointcut() {}

    // 最终切点：主切点且不是排除切点
    @Pointcut("controllerPointcut() && !excludedPointcut()")
    public void securedPointcut() {}

    @Around("securedPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            String bearerToken = request.getHeader("Authorization");
            log.debug("Token from header: {}", bearerToken);

            if (bearerToken == null) {
                log.warn("请求缺少Token");
                return R.fail("请先登录");
            }

            String token = jwtTokenUtil.resolveToken(bearerToken);

            if (!jwtTokenUtil.validateToken(token)) {
                log.warn("非法或过期的token: {}", token);
                return R.fail("Token无效或已过期");
            }
            log.debug("token 合法");
            // 存储用户信息
            String username = jwtTokenUtil.getUsername(token);
            userContext.setCurrentUser(username);

            return joinPoint.proceed();
        } finally {
            userContext.clear();
        }
    }
}