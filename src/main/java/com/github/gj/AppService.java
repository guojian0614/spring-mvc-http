package com.github.gj;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


@Service
public class AppService {
    @Autowired
    private UserService userService;


    public void get(){
        String aa = userService.get(System.currentTimeMillis()+"");
        System.out.println(aa);
    };



}
