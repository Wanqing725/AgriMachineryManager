package org.agrimachinerymanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.agrimachinerymanager.mapper")
public class AgriMachineryManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgriMachineryManagerApplication.class, args);
    }

}
