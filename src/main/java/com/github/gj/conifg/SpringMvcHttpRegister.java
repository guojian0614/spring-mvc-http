package com.github.gj.conifg;

import com.github.gj.annotation.SpringMvcHttpScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.StringUtils;


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
        scanner.doScan(basePackage);
    }

}
