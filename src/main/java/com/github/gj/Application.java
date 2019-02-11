package com.github.gj;


import com.github.gj.annotation.SpringMvcHttpScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringMvcHttpScan(basePackage = "")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Application.class,args);
      AppService appService = configurableApplicationContext.getBean(AppService.class);
//      UserService userService = configurableApplicationContext.getBean(UserService.class);
        appService.get();
    }

}

