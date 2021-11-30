package com.hacc2021.searchenginebandits.animalqueue.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableAutoConfiguration
public class InformationController {

    @GetMapping("/information")
    public String showInformation() {
        return "information";
    }
}
