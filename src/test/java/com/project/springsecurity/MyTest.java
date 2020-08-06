package com.project.springsecurity;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
@SpringBootTest
public class MyTest {
    @Autowired
    RedisTemplate redisTemplate; //操作key-value都是字符串，最常用
    @Test
    public void test01(){
        //  RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        //字符串操作
        redisTemplate.opsForValue().append("msg123","coder");
        //列表操作
//        stringRedisTemplate.opsForList().leftPush("mylist","1");
//        stringRedisTemplate.opsForList().leftPush("mylist","2");





    }
}
