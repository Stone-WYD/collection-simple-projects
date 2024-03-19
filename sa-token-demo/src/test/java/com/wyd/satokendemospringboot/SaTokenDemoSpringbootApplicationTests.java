package com.wyd.satokendemospringboot;

import com.wyd.satokendemospringboot.demos.entity.MyUser;
import com.wyd.satokendemospringboot.demos.service.MyUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SaTokenDemoSpringbootApplicationTests {

    @Resource
    private MyUserService myUserService;

    @Test
    void contextLoads() {
        MyUser myUser = myUserService.queryById(4L);
        Assertions.assertEquals(null, myUser);
    }

}
