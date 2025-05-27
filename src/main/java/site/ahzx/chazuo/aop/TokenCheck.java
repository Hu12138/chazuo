package site.ahzx.chazuo.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenCheck {
    boolean required() default true;
}
