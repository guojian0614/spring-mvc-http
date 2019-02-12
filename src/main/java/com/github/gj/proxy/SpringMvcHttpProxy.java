package com.github.gj.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import java.lang.reflect.Method;

public class SpringMvcHttpProxy implements MethodInterceptor{


    public <T> T getProxy(Class<T> type) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class[]{type});
        enhancer.setSuperclass(type);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
        if (methodMapping == null){
            return null;
        }
        return HttpUtils.autoHttp(o,method,objects);
    }

}
