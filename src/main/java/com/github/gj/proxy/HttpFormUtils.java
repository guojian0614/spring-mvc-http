package com.github.gj.proxy;

import lombok.Data;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author guojian
 * @date 2018/9/19 上午1:00
 */
public class HttpFormUtils {
    private HttpFormUtils(){}

    private final static int MAX_DEPTH = 20;

    static List<NameValuePair> get(String name , Object value) {
        try {
            return get(name,value,0);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<NameValuePair> get(String name , Object value , int depth) throws IllegalAccessException {
        if (MAX_DEPTH > 20){
            throw new RuntimeException("纵深超过20层");
        }
        List<NameValuePair> params = new ArrayList<>(8);
        if (value == null){
            return params;
        }
        Class<?> type = value.getClass();
        if (ClassUtils.isPrimitiveOrWrapper(type)){
            if (StringUtils.isNotBlank(name)){
                params.add(new BasicNameValuePair(name,value+""));
            }
            return params;
        }
        if (value instanceof String){
            if (StringUtils.isNotBlank(name)){
                params.add(new BasicNameValuePair(name,value+""));
            }
            return params;
        }
        if (type == BigDecimal.class){
            if (StringUtils.isNotBlank(name)){
                params.add(new BasicNameValuePair(name,value+""));
            }
            return params;
        }
        if (value instanceof Date){
            params.add(new BasicNameValuePair(name,((Date)value).getTime()+""));
            return params;
        }
        if (type.isArray()){
            int length = Array.getLength(value);
            for (int i = 0; i < length; i++) {
                Object valueTemp = Array.get(value,i);
                String nameTemp = String.format("%s[%s]",name,i);
                List<NameValuePair> paramTemp = get(nameTemp,valueTemp,depth+1);
                params.addAll(paramTemp);
            }
            return params;
        }
        if (List.class.isAssignableFrom(type)){
            List listTemp = (List)value;
            for (int i = 0; i < listTemp.size(); i++) {
                Object valueTemp = listTemp.get(i);
                String nameTemp = String.format("%s[%s]",name,i);
                List<NameValuePair> paramTemp = get(nameTemp,valueTemp,depth+1);
                params.addAll(paramTemp);
            }
            return params;
        }
        StringBuilder paramsName = new StringBuilder();
        if (depth > 0){
            paramsName.append(name);
            paramsName.append(".");
        }
        if (value instanceof Map){
            Map<String,Object> mapTemp = (Map<String,Object>)value;
            for (Map.Entry<String,Object> entry : mapTemp.entrySet()) {
               String key = entry.getKey();
                Object valueTemp = entry.getValue();
                String nameTemp = String.format("%s%s",paramsName,key);
                List<NameValuePair> paramsTemp = get(nameTemp,valueTemp,depth+1);
                params.addAll(paramsTemp);
            }
            return params;
        }
        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            String fieldName = field.getName();
            String nameTemp = String.format("%s%s",paramsName,fieldName);
            List<NameValuePair> paramsTemp = get(nameTemp,field.get(value),depth+1);
            params.addAll(paramsTemp);
        }
        return params;
    }
    @Data
    static class A{

        private Integer a;

        private BB[] barray;

        private BB b;

    }
    @Data
    static class BB{

        private int c;

        private C[] carray;

        private List<C> clist;
    }
    @Data
    static class C{

        private long id;

        private String name;

    }

    public static void main(String[] args) {
        A a = new A();
        a.setA(1);
        BB[] barray = new BB[]{new BB(),new BB()};
        a.setBarray(barray);
        a.setB(new BB());
        List<NameValuePair> list = get("",a);
        for (NameValuePair nameValuePair : list) {
            System.out.println(nameValuePair.getName()+"="+nameValuePair.getValue());
        }
    }

}
