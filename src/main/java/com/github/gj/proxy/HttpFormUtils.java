package com.github.gj.proxy;

<<<<<<< HEAD
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
=======
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.ClassUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
>>>>>>> bde3cf5... 修改

/**
 * @author guojian
 * @date 2018/9/19 上午1:00
 */
<<<<<<< HEAD
public class HttpFormUtils {
=======
class HttpFormUtils {
>>>>>>> bde3cf5... 修改
    private HttpFormUtils(){}

    private final static int MAX_DEPTH = 20;

<<<<<<< HEAD
    static List<NameValuePair> get(String name , Object value) {
=======
    public static List<NameValuePair> get(String name , Object value) {
>>>>>>> bde3cf5... 修改
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
<<<<<<< HEAD
                String nameTemp = String.format("%s[%s]",name,i);
                List<NameValuePair> paramTemp = get(nameTemp,valueTemp,depth+1);
=======
                String nameTemp;
                if (depth > 0){
                    nameTemp = String.format("%s[%s]",name,i);
                }else {
                    nameTemp = String.format("%s",name);
                }
                List<NameValuePair> paramTemp = get(nameTemp,valueTemp,1+depth);
>>>>>>> bde3cf5... 修改
                params.addAll(paramTemp);
            }
            return params;
        }
        if (List.class.isAssignableFrom(type)){
            List listTemp = (List)value;
            for (int i = 0; i < listTemp.size(); i++) {
                Object valueTemp = listTemp.get(i);
                String nameTemp = String.format("%s[%s]",name,i);
<<<<<<< HEAD
                List<NameValuePair> paramTemp = get(nameTemp,valueTemp,depth+1);
=======
                List<NameValuePair> paramTemp = get(nameTemp,valueTemp,1+depth);
>>>>>>> bde3cf5... 修改
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
<<<<<<< HEAD
                List<NameValuePair> paramsTemp = get(nameTemp,valueTemp,depth+1);
=======
                List<NameValuePair> paramsTemp = get(nameTemp,valueTemp,1+depth);
>>>>>>> bde3cf5... 修改
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
<<<<<<< HEAD
            List<NameValuePair> paramsTemp = get(nameTemp,field.get(value),depth+1);
=======
            List<NameValuePair> paramsTemp = get(nameTemp,field.get(value),1+depth);
>>>>>>> bde3cf5... 修改
            params.addAll(paramsTemp);
        }
        return params;
    }
<<<<<<< HEAD
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
=======

    public static void main(String[] args) {

        A a = new A();
        a.setCcc("rewrewrweprjew");
        C c = new C();
        c.setDate(new Date());
        c.setUser2(new Date());
        C c2 = new C();
        c2.setDate(new Date());
        c2.setUser2(new Date());
        B b = new B();
        b.setA(1);
        b.setC(c);
        b.setCc(9L);
        B b2 = new B();
        b2.setA(1);
        b2.setC(c);
        b2.setCc(9L);
        C[] carra = new C[]{c,c2};
        a.setCarray(carra);
        a.setBlist(Arrays.asList(b,b2));
       List<NameValuePair> list = get("CARRA",carra);
        for (NameValuePair nameValuePair : list) {
            System.out.println(nameValuePair.getName()+":"+nameValuePair.getValue());
        }
        list = get("CARRA",a);
        for (NameValuePair nameValuePair : list) {
            System.out.println(nameValuePair.getName()+":"+nameValuePair.getValue());
        }

    }

    @Data
    static class A{

        private String ccc;

        private List<B> blist;

        private C[] carray;

    }

    @Data
    static class B{

        private C c;

        private int a;

        private Long cc;
    }


    @Data
    static class C{

        private Date date;

        private Date user2;
    }

>>>>>>> bde3cf5... 修改

}
