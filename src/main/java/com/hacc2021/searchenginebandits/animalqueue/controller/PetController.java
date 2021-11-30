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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PetController {
    final OwnerService ownerService;

    final PetService petService;

    @GetMapping("/pets/{petId}")
    public String showPet(@PathVariable("petId") final int petId, final Model model) {
        try {
            final Pet pet = findPet(petId);
            model.addAttribute("pet", pet);
            return "showPet";
        } catch (final NotFoundException e) {
            return "redirect:/owners";
        }
    }

    @GetMapping("/owners/{ownerId}/newPet")
    public String newPet(@PathVariable("ownerId") final int ownerId, final Model model) {
        final Owner owner = findOwner(ownerId);
        model.addAttribute("owner", owner);
        return "newPet";
    }

    @PostMapping("/owners/{ownerId}/newPet")
    public String createPet(@PathVariable("ownerId") final int ownerId,
                            @RequestParam("name") final String name,
                            @RequestParam(value = "chipNo") final String chipNo) {
        final Owner owner = findOwner(ownerId);
        petService.createPet(owner, name, chipNo);
        return "redirect:/owners/" + ownerId;
    }

    @GetMapping("/pets/{petId}/delete")
    public String confirmPetDeletion(@PathVariable("petId") final int petId, final Model model) {
        final Pet pet = findPet(petId);
        model.addAttribute("name", String.format("Pet: %s", pet.getName()));
        model.addAttribute("description",
                           String.format("Owner: %s%nChip no.: %s", pet.getOwner().getName(), pet.getChipNo()));
        model.addAttribute("redirect", "/owners/" + pet.getOwner().getId());
        return "confirmDeletion";
    }

    @DeleteMapping("/pets/{petId}/delete")
    @ResponseBody
    public String deletePet(@PathVariable("petId") final int petId) {
        final Pet pet = findPet(petId);
        final int ownerId = pet.getOwner().getId();
        petService.deletePet(pet);
        return "redirect:/owners/" + ownerId;
    }

    private Owner findOwner(final int ownerId) {
        final Optional<Owner> possibleOwner = ownerService.findById(ownerId);
        if (possibleOwner.isEmpty()) {
            throw new NotFoundException("Owner not found.");
        }
        return possibleOwner.get();
    }

    private Pet findPet(final int petId) {
        final Optional<Pet> possiblePet = petService.findById(petId);
        if (possiblePet.isEmpty()) {
            throw new NotFoundException("Pet not found");
        }
        return possiblePet.get();
    }
}
