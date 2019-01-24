package com.github.gj.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface SocketTimeOut {
    int value();
    Class<RuntimeException> exception();
}
