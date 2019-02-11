package com.github.gj;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @RequestMapping("/test")
    public String get(String a , List<String> list , int[] array){
        return "success";
    };

}
