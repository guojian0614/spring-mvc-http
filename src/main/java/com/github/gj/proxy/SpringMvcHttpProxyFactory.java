package com.github.gj.proxy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

public class SpringMvcHttpProxyFactory<T> implements FactoryBean<T> {
    @Getter
    @Setter
    private Class<T> interfaceClass;

    @Override
    public T getObject() throws Exception {
        return new SpringMvcHttpProxy().getProxy(interfaceClass);
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
