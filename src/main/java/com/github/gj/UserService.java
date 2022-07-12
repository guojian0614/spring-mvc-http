package com.github.gj;


import com.github.gj.annotation.SpringMvcHttpClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@SpringMvcHttpClient(host = "${}")
@RequestMapping("")
public interface UserService {


    String get( @RequestBody String rewrewrw );


}
