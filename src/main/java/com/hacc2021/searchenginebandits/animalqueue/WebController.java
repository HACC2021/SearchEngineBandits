package com.hacc2021.searchenginebandits.animalqueue;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableAutoConfiguration
public class WebController {
    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("message", "Hello world!");
        return "helloworld";
    }
}
