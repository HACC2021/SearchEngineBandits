package com.hacc2021.searchenginebandits.animalqueue.controller;

import com.hacc2021.searchenginebandits.animalqueue.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WebController {
    final OwnerService ownerService;

    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("message", "Hello world!");
        return "helloworld";
    }

    @GetMapping("/testSomething/{name}")
    public String testSomething(@PathVariable(name = "name") final String name) {
        ownerService.createOwner(name);
        return "helloworld";
    }

    @GetMapping("/owners")
    public String listOwners(final Model model) {
        model.addAttribute("owners", ownerService.listOwners());
        return "ownerList";
    }
}
