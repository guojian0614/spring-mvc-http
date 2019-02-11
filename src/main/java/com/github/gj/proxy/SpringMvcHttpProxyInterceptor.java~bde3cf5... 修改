package com.github.gj.proxy;

import com.alibaba.fastjson.JSONObject;
import com.github.gj.annotation.SpringMvcHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.NameValuePair;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SpringMvcHttpProxyInterceptor implements MethodInterceptor {

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
            log.info("{} is not proxy method",method.getName());
            return String.format("%s is not proxy method",method.getName());
        }
        return autoHttp(o,method,objects);
    }

    private Object autoHttp(Object o, Method method, Object[] objects)throws Throwable{
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
        List<NameValuePair> list = new ArrayList<>(10);
        String body;
        String name;
        int bodyCount = 0;
        RequestBody requestBody;
        int index = -1;
        Parameter parameter;
        for (int i = 0,length = parameters.length; i < length; i++) {
            parameter = parameters[i];
            requestBody = parameter.getAnnotation(RequestBody.class);
            if (bodyCount > 1){
                throw new RuntimeException("RequestBody Annotation more than one");
            }
            if (requestBody != null){
                body = JSONObject.toJSONString(parameter);
            }

            name = getParameterName(parameter);
        }
        return null;
    }

    private Object post(String url ,RequestMethod requestMethod, Map<String,Object> params , Map<String,Object> body,Class<?> retutnType){
        return null;
    }

    private String getParameterName(Parameter parameter)throws Throwable{
        String name = null;
        RequestParam requestParam;
        requestParam = parameter.getAnnotation(RequestParam.class);
        if (requestParam != null){
            name = requestParam.name();
        }
        if (StringUtils.isEmpty(name)){
            name = parameter.getName();
        }
        if (StringUtils.isEmpty(name)){
            throw new RuntimeException("parameter name is empty");
        }
        return name;
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
