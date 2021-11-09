package com.hacc2021.searchenginebandits.animalqueue.controller;

import com.hacc2021.searchenginebandits.animalqueue.exception.NotFoundException;
import com.hacc2021.searchenginebandits.animalqueue.model.Pet;
import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.model.StateType;
import com.hacc2021.searchenginebandits.animalqueue.service.PetService;
import com.hacc2021.searchenginebandits.animalqueue.service.QuarantineService;
import com.hacc2021.searchenginebandits.animalqueue.service.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class QuarantineAdministrationController {
    final PetService petService;

    final QuarantineService quarantineService;

    final StateService stateService;

    @GetMapping("/quarantines/{quarantineId}")
    public String manageQuarantine(@PathVariable("quarantineId") final int quarantineId, final Model model) {
        final Optional<Quarantine> possibleQuarantine = quarantineService.findById(quarantineId);
        if (possibleQuarantine.isEmpty()) {
            throw new NotFoundException("Quarantine not found.");
        }
        final Quarantine quarantine = possibleQuarantine.get();
        model.addAttribute("quarantine", quarantine);
        model.addAttribute("collectionTimeRequestable",
                           quarantine.getCurrentState().getType() == StateType.COLLECTION_TIME_REQUESTABLE);
        return "manageQuarantine";
    }

    @PostMapping("/quarantines/{quarantineId}")
    public String changeQuarantineState(@PathVariable("quarantineId") final int quarantineId,
                                        @RequestParam("succeedingState") final StateType succeedingState,
                                        @RequestParam(value = "payloadText", required = false) final String payloadText,
                                        @RequestParam(value = "payloadDateTime", required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        final LocalDateTime payloadDateTime,
                                        final Model model) {
        final Optional<Quarantine> possibleQuarantine = quarantineService.findById(quarantineId);
        if (possibleQuarantine.isEmpty()) {
            throw new NotFoundException("Quarantine not found.");
        }
        final Quarantine quarantine = possibleQuarantine.get();
        stateService.addState(quarantine,
                              succeedingState,
                              new StateService.Payload(succeedingState.hasPayloadText() ? payloadText : null,
                                                       succeedingState.hasPayloadDateTime() ? payloadDateTime : null));

        if (succeedingState == StateType.COLLECTED) {
            quarantineService.endQuarantine(quarantine);
        }

        return manageQuarantine(quarantineId, model);
    }

    @GetMapping("/pets/{petId}/newQuarantine")
    public ModelAndView createQuarantine(@PathVariable("petId") final int petId, final Model model) {
        final Optional<Pet> possiblePet = petService.findById(petId);
        if (possiblePet.isEmpty()) {
            throw new NotFoundException("Pet not found.");
        }
        final Pet pet = possiblePet.get();
        quarantineService.createQuarantine(pet);
        return new ModelAndView("redirect:/pets/" + petId, model.asMap());
    }

}
