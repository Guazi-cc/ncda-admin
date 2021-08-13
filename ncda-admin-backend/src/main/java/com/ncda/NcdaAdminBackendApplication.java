package com.ncda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ncda.dao")
public class NcdaAdminBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NcdaAdminBackendApplication.class, args);
    }

}
