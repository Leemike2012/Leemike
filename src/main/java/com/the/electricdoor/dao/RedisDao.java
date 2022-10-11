package com.the.electricdoor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisDao {

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    public String getByKey(String key){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(key);
        if(value == null){
            return null;
        }
        return String.valueOf(value);
    }

    public void add(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public String getHashByKey(String key, String field){
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String value = hashOperations.get(key, field);
        if(value == null){
            return null;
        }
        return String.valueOf(value);
    }

    public void addHashByKey(String key, String field, String value){
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, field, value);
    }
}
