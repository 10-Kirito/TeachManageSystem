package com.example.teachmanagesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@MapperScan("com.example.teachmanagesystem.mapper")
public class TeachManageSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachManageSystemApplication.class, args);
    }

}
