package com.github.gj.proxy;

import com.github.gj.annotation.SpringMvcHttpClient;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpringMvcHttpProxy implements MethodInterceptor {

    private Environment environment;

    private static final String REGEX = "(?<=(\\$\\{))[\\w\\W]*(?=(\\}))";
    private static final Pattern PATTERN = Pattern.compile(REGEX);


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
            return o.toString();
        }
        return autoHttp(o,method,objects);
    }

    private Object autoHttp(Object o, Method method, Object[] objects){
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

    private Object post(String url ,RequestMethod requestMethod, Map<String,Object> params , Map<String,Object> body){
        return null;
    }

    private String getMethod(RequestMapping methodMapping,RequestMapping classMapping){
        StringBuilder path = new StringBuilder();
        List<String> list = new ArrayList<>();
        path.append(getPath(classMapping));
        path.append(getPath(methodMapping));
        return path.toString();
    }

    private String getPath(RequestMapping methodMapping,RequestMapping classMapping){
        StringBuilder path = new StringBuilder();
        List<String> list = new ArrayList<>();
        path.append(getPath(classMapping));
        path.append(getPath(methodMapping));
        return path.toString();
    }

    private String getPath(RequestMapping mapping){
        String defaultReturn = "";
        if (mapping == null){
            return defaultReturn;
        }
        String[] paths = mapping.value();
        if (paths.length < 1){
            return defaultReturn;
        }
        return paths[0];
    }

    private String getHost(String hostKey){
        String host;
        String key = null;
        Matcher matcher = PATTERN.matcher(hostKey);
        boolean flag = matcher.find();
        if (flag){
            key = matcher.group();
        }
        if (key == null){
            host=environment.getProperty(key);
            return host;
        }
        host = hostKey;
        return host;
    }
}
