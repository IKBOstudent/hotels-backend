package ru.rschir.hotelsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelsBackendApplication {

    public static void main(String[] args) {
        var app = new SpringApplication(HotelsBackendApplication.class);
        app.run(args);
    }

}
