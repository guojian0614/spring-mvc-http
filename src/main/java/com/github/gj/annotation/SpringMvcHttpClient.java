package com.github.gj.annotation;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface SpringMvcHttpClient {

    String host() default "";

}
