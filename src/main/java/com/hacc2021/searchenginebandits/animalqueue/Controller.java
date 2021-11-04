package com.hacc2021.searchenginebandits.animalqueue;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Controller {
    
    @GetMapping("/")
    public String index() {
        return "Hello world!";
    }
}
