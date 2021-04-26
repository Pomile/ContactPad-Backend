package com.cp.contactpad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ContactPadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactPadApplication.class, args);
    }

    @GetMapping("/api")
    public String server() {
        return "<h3>Server is running...<h3>";
    }

}
