package com.github.gj.conifg;

import com.github.gj.annotation.SpringMvcHttpClient;
import com.github.gj.proxy.SpringMvcHttpProxyFactory;
import org.springframework.asm.ClassReader;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;


public class SpringMvcHttpClassPathScanner extends ClassPathBeanDefinitionScanner {

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private final BeanDefinitionRegistry registry;

    private final Environment environment;

    public SpringMvcHttpClassPathScanner(BeanDefinitionRegistry registry , Environment environment){
        super(registry);
        this.registry = registry;
        this.environment = environment;
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }

    @Override
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> set = null;
        try {
            set = scanCandidateComponents(basePackage);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        return set;
    }

    private Set<BeanDefinition> scanCandidateComponents(String basePackage) throws ClassNotFoundException, IOException {
        LinkedHashSet candidates = new LinkedHashSet();
        String packageSearchPath = "classpath*:" + super.resolveBasePackage(basePackage) + '/' + "**/*.class";
        Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
        Resource[] var7 = resources;
        int var8 = resources.length;
        Resource resource;
        for (int var9 = 0; var9 < var8; ++var9) {
            resource = var7[var9];
            register(resource,candidates);
        }
        return candidates;
    }

    private void register(Resource resource,LinkedHashSet candidates) throws ClassNotFoundException, IOException {
        if (!resource.isReadable()) {
            return;
        }
        ClassReader classReader = new ClassReader(resource.getInputStream());
        String typeName = classReader.getClassName();
        Class type = Class.forName(typeName.replace("/", "."));
        String classSimple = type.getSimpleName();
        if (!type.isInterface()) {
            return;
        }
        if (! type.isAnnotationPresent(SpringMvcHttpClient.class)) {
            return;
        }
        String beanName = beanName(classSimple);
        // 需要被代理的接口
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(type);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getBeanDefinition();
        definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
        definition.setBeanClass(SpringMvcHttpProxyFactory.class);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        registry.registerBeanDefinition(beanName, definition);
    }

    private String beanName(String str){
        char[] chars = str.toCharArray();
        StringBuilder builder = new StringBuilder(chars.length);
        for (int i = 0 , length = chars.length; i < length; i++) {
            if (i == 0){
                builder.append(Character.toLowerCase(chars[i]));
                continue;
            }
            builder.append(chars[i]);
        }
        return builder.toString();
    }
}

