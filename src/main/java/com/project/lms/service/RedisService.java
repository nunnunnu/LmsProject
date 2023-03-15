package com.project.lms.service;
import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService { //레디스에 토큰을 저장/조회/삭제하기 위한 서비스

    private final RedisTemplate redisTemplate;

    // 키-벨류 설정
    public void setValues(String token, String id){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        //values.set(name, age);
        values.set(token, id, Duration.ofMinutes(60*24*24));  //3분뒤 메모리에서 삭제
    }

    // 키값으로 벨류 가져오기
    public String getValues(String token){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(token);
    }

    // 키-벨류 삭제
    public void delValues(String token) {
        redisTemplate.delete(token);
    }
}

