package com.github.gj.annotation;

import org.springframework.stereotype.Component;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface SpringMvcHttpClient {

    String host() default "";

}
