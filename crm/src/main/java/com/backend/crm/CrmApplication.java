package com.backend.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * TODO: Сделать проверку токена
 */

/**
 * ## API для crm системы по учету организационной техники
 *
 * @author Горелов Дмитрий
 */

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class, args);
    }
}
