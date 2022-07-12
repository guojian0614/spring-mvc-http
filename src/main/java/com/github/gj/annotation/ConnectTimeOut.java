package com.github.gj.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ConnectTimeOut {
    int value();
    Class<RuntimeException> exception();
}
