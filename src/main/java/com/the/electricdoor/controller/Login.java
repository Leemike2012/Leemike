package com.the.electricdoor.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;


import com.the.electricdoor.dto.LoginDto;
import com.the.electricdoor.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

    @Autowired
    UserService userService;
    
    @PostMapping()
    public LoginDto login(String user_name, String passwd){

        boolean rs = userService.login(user_name, passwd);

        LoginDto dto = new LoginDto();
        
        if(rs==true){
        dto.setResult(rs);
        String uuid = UUID.randomUUID().toString();
        userService.addToken(uuid, user_name);
        
        dto.setToken(uuid);

        dto.setMessage("成功");
        
        } else{
            dto.setMessage("失败");
        }

        return dto;
    }
    
    


    @PostMapping("/login/test")
    public String test(){
        return "test";
    }
}