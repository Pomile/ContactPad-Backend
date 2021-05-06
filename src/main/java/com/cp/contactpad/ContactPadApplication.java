package com.cp.contactpad;

import com.cp.contactpad.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableConfigurationProperties(ApplicationProperties.class)
public class ContactPadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactPadApplication.class, args);
    }

    @GetMapping("/api")
    public String server() {
        return "<h3>Server is running...<h3>";
    }

}
