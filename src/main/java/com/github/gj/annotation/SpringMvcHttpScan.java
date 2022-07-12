package com.github.gj.annotation;

import com.github.gj.conifg.SpringMvcHttpRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SpringMvcHttpRegister.class)
public @interface SpringMvcHttpScan {

    String basePackage() default "";
}
