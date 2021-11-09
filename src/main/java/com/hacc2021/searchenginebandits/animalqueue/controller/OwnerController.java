package com.hacc2021.searchenginebandits.animalqueue.controller;

import com.hacc2021.searchenginebandits.animalqueue.exception.NotFoundException;
import com.hacc2021.searchenginebandits.animalqueue.model.Owner;
import com.hacc2021.searchenginebandits.animalqueue.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OwnerController {
    final OwnerService ownerService;

    @GetMapping("/owners")
    public String listOwners(final Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "listOwner";
    }

    @GetMapping("/owners/{ownerId}")
    public String showOwner(@PathVariable("ownerId") final int ownerId, final Model model) {
        final Optional<Owner> possibleOwner = ownerService.findById(ownerId);
        if (possibleOwner.isEmpty()) {
            throw new NotFoundException("Owner not found");
        }
        model.addAttribute("owner", possibleOwner.get());
        return "showOwner";
    }

    @GetMapping("/owners/new")
    public String newOwner() {
        return "newOwner";
    }

    @PostMapping("/owners/new")
    public ModelAndView createOwner(@RequestParam("name") final String name,
                                    @RequestParam(value = "emailAddress", required = false) final String emailAddress,
                                    @RequestParam(value = "phoneNumber", required = false) final String phoneNumber,
                                    final Model model) {
        ownerService.createOwner(name, emailAddress, phoneNumber);
        return new ModelAndView("redirect:/owners", model.asMap());
    }
}
