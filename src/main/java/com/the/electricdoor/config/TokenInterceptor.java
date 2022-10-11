package com.the.electricdoor.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.the.electricdoor.dao.RedisDao;

@Component
public class TokenInterceptor implements HandlerInterceptor{

    @Autowired
    RedisDao redisDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
                String token = request.getHeader("token");

                if(token == null){
                    response.getWriter().print("{\"result\": false, \"message\":\"token is required\"}");
                    return false;
                }

                String user_name = redisDao.getHashByKey("token", token);

                if(user_name == null){
                    response.getWriter().print("{\"result\": false, \"message\":\"token is not exist\"}");
                    return false;
                }else{
                    return true;
                }
    }
    
    
}
