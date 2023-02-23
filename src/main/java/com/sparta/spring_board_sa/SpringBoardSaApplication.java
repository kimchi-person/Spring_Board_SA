package com.sparta.spring_board_sa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBoardSaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoardSaApplication.class, args);
    }

}
