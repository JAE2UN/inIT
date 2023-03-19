package com.ssafy.init.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebSecurityConfigTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordTest() {
        String rawPwd = "abc123";
        String encodedPwd = passwordEncoder.encode(rawPwd);


        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>test");

        assertAll(
                () -> assertNotEquals(rawPwd, encodedPwd),
                () -> assertTrue(passwordEncoder.matches(rawPwd, encodedPwd))
        );

        System.out.println("test");
    }
}