package com.hacc2021.searchenginebandits.animalqueue.controller;

import com.hacc2021.searchenginebandits.animalqueue.exception.NotFoundException;
import com.hacc2021.searchenginebandits.animalqueue.model.Owner;
import com.hacc2021.searchenginebandits.animalqueue.model.Pet;
import com.hacc2021.searchenginebandits.animalqueue.service.api.OwnerService;
import com.hacc2021.searchenginebandits.animalqueue.service.api.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PetController {
    final OwnerService ownerService;

    final PetService petService;

    @GetMapping("/pets/{petId}")
    public String showPet(@PathVariable("petId") final int petId, final Model model) {
        final Optional<Pet> possiblePet = petService.findById(petId);
        if (possiblePet.isEmpty()) {
            throw new NotFoundException("Pet not found");
        }
        model.addAttribute("pet", possiblePet.get());
        return "showPet";
    }

    @GetMapping("/owners/{ownerId}/newPet")
    public String newPet(@PathVariable("ownerId") final int ownerId, final Model model) {
        final Owner owner = getOwner(ownerId);
        model.addAttribute("owner", owner);
        return "newPet";
    }

    @PostMapping("/owners/{ownerId}/newPet")
    public String createPet(@PathVariable("ownerId") final int ownerId,
                            @RequestParam("name") final String name,
                            @RequestParam(value = "chipNo") final String chipNo) {
        final Owner owner = getOwner(ownerId);
        petService.createPet(owner, name, chipNo);
        return "redirect:/owners/" + ownerId;
    }

    private Owner getOwner(final int ownerId) {
        final Optional<Owner> possibleOwner = ownerService.findById(ownerId);
        if (possibleOwner.isEmpty()) {
            throw new NotFoundException("Owner not found.");
        }
        return possibleOwner.get();
    }
}
