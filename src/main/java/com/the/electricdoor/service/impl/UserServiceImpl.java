package com.the.electricdoor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.the.electricdoor.Entity.Acount;
import com.the.electricdoor.dao.AcountDao;
import com.the.electricdoor.dao.RedisDao;
import com.the.electricdoor.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    AcountDao acountDao;

    @Autowired
    RedisDao redisDao;

    @Override
    public boolean login(String user_name, String passwd) {
        Acount acount = acountDao.selectAcountByKey(user_name);

        boolean result = false;

        if(acount != null){
            if(acount.getAcount_password().equals(passwd)){
                result = true;
            }
        }

        return result;
    }

    @Override
    public void addToken(String token, String user_name) {
        redisDao.addHashByKey("token", token, user_name);
    }
    
}
