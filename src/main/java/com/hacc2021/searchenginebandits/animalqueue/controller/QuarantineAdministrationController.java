package com.hacc2021.searchenginebandits.animalqueue.controller;

import com.hacc2021.searchenginebandits.animalqueue.exception.NotFoundException;
import com.hacc2021.searchenginebandits.animalqueue.model.Pet;
import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.model.StateType;
import com.hacc2021.searchenginebandits.animalqueue.service.api.PetService;
import com.hacc2021.searchenginebandits.animalqueue.service.api.QuarantineService;
import com.hacc2021.searchenginebandits.animalqueue.service.api.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class QuarantineAdministrationController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy' at 'hh:mm a", Locale.US);

    private final PetService petService;

    private final QuarantineService quarantineService;

    private final StateService stateService;

    @GetMapping("/quarantines/{quarantineId}")
    public String manageQuarantine(@PathVariable("quarantineId") final int quarantineId, final Model model) {
        try {
            final Quarantine quarantine = findQuarantine(quarantineId);
            model.addAttribute("quarantine", quarantine);
            model.addAttribute("collectionTimeRequestable",
                               quarantine.getCurrentState().getType() == StateType.COLLECTION_TIME_REQUESTABLE);
            return "manageQuarantine";
        } catch (final NotFoundException e) {
            return "redirect:/owners";
        }
    }

    @PostMapping("/quarantines/{quarantineId}")
    public String changeQuarantineState(@PathVariable("quarantineId") final int quarantineId,
                                        @RequestParam("succeedingState") final StateType succeedingState,
                                        @RequestParam(value = "payloadText", required = false) final String payloadText,
                                        @RequestParam(value = "payloadDateTime", required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        final LocalDateTime payloadDateTime) {
        final Quarantine quarantine = findQuarantine(quarantineId);
        stateService.addState(quarantine,
                              succeedingState,
                              new StateService.Payload(succeedingState.hasPayloadText() ? payloadText : null,
                                                       succeedingState.hasPayloadDateTime() ? payloadDateTime : null));

        if (succeedingState == StateType.COLLECTED) {
            quarantineService.endQuarantine(quarantine);
        }

        return "redirect:/quarantines/" + quarantineId;
    }

    @GetMapping("/quarantines/{quarantineId}/delete")
    public String confirmQuarantineDeletion(@PathVariable("quarantineId") final int quarantineId, final Model model) {
        final Quarantine quarantine = findQuarantine(quarantineId);
        model.addAttribute("name", String.format("Quarantine of %s", quarantine.getPet().getName()));
        model.addAttribute("description",
                           String.format("Current state: %s%nCreation: %s%nEnding: %s",
                                         quarantine.getCurrentState().getMessage(),
                                         formatDateTime(quarantine.getCreation()),
                                         formatDateTime(quarantine.getEnding())));
        model.addAttribute("redirect", "/pets/" + quarantine.getPet().getId());
        return "confirmDeletion";
    }

    @DeleteMapping("/quarantines/{quarantineId}/delete")
    @ResponseBody
    public String deleteQuarantine(@PathVariable("quarantineId") final int quarantineId) {
        final Quarantine quarantine = findQuarantine(quarantineId);
        final int petId = quarantine.getPet().getId();
        quarantineService.deleteQuarantine(quarantine);
        return "redirect:/pets/" + petId;
    }

    @GetMapping("/pets/{petId}/newQuarantine")
    public String createQuarantine(@PathVariable("petId") final int petId) {
        final Optional<Pet> possiblePet = petService.findById(petId);
        if (possiblePet.isEmpty()) {
            throw new NotFoundException("Pet not found.");
        }
        final Pet pet = possiblePet.get();
        quarantineService.createQuarantine(pet);
        return "redirect:/pets/" + petId;
    }

    private Quarantine findQuarantine(@PathVariable("quarantineId") final int quarantineId) {
        final Optional<Quarantine> possibleQuarantine = quarantineService.findById(quarantineId);
        if (possibleQuarantine.isEmpty()) {
            throw new NotFoundException("Quarantine not found.");
        }
        return possibleQuarantine.get();
    }

    private static String formatDateTime(final LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return FORMATTER.format(dateTime);
    }

}
