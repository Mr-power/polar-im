package com.aias.polar.jwt.config;

import com.aias.polar.jwt.inteceptor.JwtInteceptor;
import com.aias.polar.jwt.utils.JwtUtils;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuhy
 * @Classname EnableJwt
 * @Description TODO
 * @Date 2019/12/15 17:03
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({JwtAutoConfiguration.class, JwtUtils.class, JwtInteceptor.class})
public @interface EnableJWT {
}
