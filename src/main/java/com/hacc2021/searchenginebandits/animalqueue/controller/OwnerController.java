package com.hacc2021.searchenginebandits.animalqueue.controller;

import com.hacc2021.searchenginebandits.animalqueue.exception.NotFoundException;
import com.hacc2021.searchenginebandits.animalqueue.model.Owner;
import com.hacc2021.searchenginebandits.animalqueue.service.api.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        try {
            final Owner owner = findOwner(ownerId);
            model.addAttribute("owner", owner);
            return "showOwner";
        } catch (final NotFoundException e) {
            return "redirect:/owners";
        }
    }

    @GetMapping("/owners/new")
    public String newOwner() {
        return "newOwner";
    }

    @PostMapping("/owners/new")
    public String createOwner(@RequestParam("name") final String name,
                              @RequestParam(value = "emailAddress", required = false) final String emailAddress,
                              @RequestParam(value = "phoneNumber", required = false) final String phoneNumber) {
        ownerService.createOwner(name, emailAddress, phoneNumber);
        return "redirect:/owners";
    }

    @GetMapping("/owners/{ownerId}/delete")
    public String confirmOwnerDeletion(@PathVariable("ownerId") final int ownerId, final Model model) {
        final Owner owner = findOwner(ownerId);
        model.addAttribute("name", String.format("Owner: %s", owner.getName()));
        model.addAttribute("description",
                           String.format("Email address: %s%nPhone number: %s",
                                         owner.getEmailAddress(),
                                         owner.getPhoneNumber()));
        model.addAttribute("redirect", "/owners");
        return "confirmDeletion";
    }

    @DeleteMapping("/owners/{ownerId}/delete")
    @ResponseBody
    public String deleteOwner(@PathVariable("ownerId") final int ownerId) {
        final Owner owner = findOwner(ownerId);
        ownerService.deleteOwner(owner);
        return "redirect:/owners/";
    }

    private Owner findOwner(final int ownerId) {
        final Optional<Owner> possibleOwner = ownerService.findById(ownerId);
        if (possibleOwner.isEmpty()) {
            throw new NotFoundException("Owner not found");
        }
        return possibleOwner.get();
    }
}
