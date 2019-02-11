package com.github.gj.proxy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

public class SpringMvcHttpProxyFactory<T> implements FactoryBean<T> {
    @Getter
    @Setter
    private Class<T> interfaceClass;

    private static final SpringMvcHttpProxy SPRING_MVC_HTTP_PROXY = new SpringMvcHttpProxy();

    public SpringMvcHttpProxyFactory(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public T getObject() throws Exception {
<<<<<<< HEAD
        return SPRING_MVC_HTTP_PROXY.getProxy(interfaceClass);
=======
        return new SpringMvcHttpProxyInterceptor().getProxy(interfaceClass);
>>>>>>> bde3cf5... 修改
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        // 单例模式
        return true;
    }

}
