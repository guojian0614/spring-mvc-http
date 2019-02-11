package com.github.gj.conifg;

import com.github.gj.UserService;
import com.github.gj.annotation.SpringMvcHttpClient;
import com.github.gj.proxy.SpringMvcHttpProxyFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
<<<<<<< HEAD
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import java.util.Arrays;
import java.util.Iterator;
=======
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import java.io.IOException;
import java.util.LinkedHashSet;
>>>>>>> bde3cf5... 修改
import java.util.Set;



public class SpringMvcHttpClassPathScanner extends ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    private final Environment environment;

    public SpringMvcHttpClassPathScanner(BeanDefinitionRegistry registry , Environment environment){
        super(registry);
        this.registry = registry;
        this.environment = environment;
    }

    public void registerFilters() {
        this.resetFilters(false);
        this.addIncludeFilter((MetadataReader metadataReader,MetadataReaderFactory metadataReaderFactory )-> ! metadataReader.getClassMetadata().isAnnotation() && metadataReader.getAnnotationMetadata().hasAnnotation(SpringMvcHttpClient.class.getName()));
        this.addExcludeFilter((MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) -> metadataReader.getClassMetadata().getClassName().endsWith("package-info"));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            this.logger.warn("No SpringMvcHttpClient was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            this.processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        Iterator var3 = beanDefinitions.iterator();
        while(var3.hasNext()) {
            BeanDefinitionHolder holder = (BeanDefinitionHolder) var3.next();
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Creating SpringMvcHttpClient with name '" + holder.getBeanName() + "' and '" + definition.getBeanClassName() + "' mapperInterface");
            }
            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
            definition.setBeanClass(SpringMvcHttpProxyFactory.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            this.logger.warn("Skipping SpringMvcHttpClient with name '" + beanName + "' and '" + beanDefinition.getBeanClassName() + "' mapperInterface. Bean already defined with the same name!");
            return false;
        }
    }
<<<<<<< HEAD


=======
>>>>>>> bde3cf5... 修改
}

