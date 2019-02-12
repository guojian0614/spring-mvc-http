package com.github.gj.proxy;

import com.github.gj.annotation.SpringMvcHttpClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guojian
 * @date 2019/2/12 上午10:20
 */

class HttpUtils implements ApplicationContextAware, EnvironmentAware {

    private static final String REGEX = "(?<=(\\$\\{))[\\w\\W]*(?=(\\}))";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private static Environment environment;

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    static Object autoHttp(Object o, Method method, Object[] objects){
        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
        Class<?> c = method.getDeclaringClass();
        SpringMvcHttpClient springMvcHttpClient = c.getAnnotation(SpringMvcHttpClient.class);
        RequestMapping classMapping = c.getAnnotation(RequestMapping.class);
        String hostKey = springMvcHttpClient.host();
        String host = getHost(hostKey);
        String path = getPath(methodMapping,classMapping);
        String url = String.format("%s%s",host,path);
        RequestMethod requestMethod;
        Parameter[] parameters = method.getParameters();
        Class<?> type = o.getClass();
        Class<?> retutnType = method.getReturnType();
        return null;
    }

    private static String getPath(RequestMapping methodMapping,RequestMapping classMapping){
        StringBuilder path = new StringBuilder();
        path.append(getPath(classMapping));
        path.append(getPath(methodMapping));
        return path.toString().replaceAll("//","/");
    }

    private static String getPath(RequestMapping mapping){
        final String prefix = "/";
        final String defaultReturn = "";
        if (mapping == null){
            return defaultReturn;
        }
        String[] paths = mapping.value();
        if (paths.length < 1){
            return defaultReturn;
        }
        String path = paths[0];
        if (path.startsWith(prefix)){
            return paths[0];
        }
        return prefix+path;
    }

    private static String getHost(String hostKey) {
        String host;
        String key = null;
        Matcher matcher = PATTERN.matcher(hostKey);
        boolean flag = matcher.find();
        if (flag) {
            key = matcher.group();
        }
        if (key == null) {
            host = hostKey;
            return host;
        }
        host = environment.getProperty(key);
        return host;
    }

}
