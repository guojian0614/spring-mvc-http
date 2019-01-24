package com.github.gj.conifg;

import com.github.gj.AppService;
import com.github.gj.annotation.SpringMvcHttpScan;
import com.github.gj.proxy.SpringMvcHttpProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;


public class SpringMvcHttpRegister implements ImportBeanDefinitionRegistrar ,EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        StandardAnnotationMetadata standardAnnotationMetadata = (StandardAnnotationMetadata)annotationMetadata;
        Class<?> type = standardAnnotationMetadata.getIntrospectedClass();
        SpringMvcHttpScan springMvcHttpScan = type.getAnnotation(SpringMvcHttpScan.class);
        String basePackage = springMvcHttpScan.basePackage();
        if (StringUtils.isEmpty(basePackage)){
            Package p = type.getPackage();
            basePackage = p.getName();
        }
        SpringMvcHttpClassPathScanner scanner = new SpringMvcHttpClassPathScanner(beanDefinitionRegistry,environment);
        scanner.doScan("com.github");
    }

    public static void main(String[] args) {
        Method[] methods = A.class.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }
    static class A{

        public void get(){

        }

        private void set(){

        }
    }

}
