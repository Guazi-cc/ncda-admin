package com.ncda.ncdaadminbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncda.entity.AccountBill;
import com.ncda.service.AcBiService;
import com.ncda.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class NcdaAdminBackendApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {

    }

    @Test
    void redisTest() {
        redisUtil.set("test", "测试 ");
        System.out.println(redisUtil.get("test"));
    }

}
