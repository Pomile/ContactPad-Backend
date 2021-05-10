package com.cp.contactpad;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ContactPadApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Using TDD approach is cool!");
        // System.out.printf("database url: %s%n", env.getProperty("spring.datasource.url"));
        // System.out.printf("JAVA_HOME: %s", env.getProperty("app.java.home"));
    }

}
