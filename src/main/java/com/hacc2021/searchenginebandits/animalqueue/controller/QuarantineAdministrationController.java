package com.hacc2021.searchenginebandits.animalqueue.controller;

import com.hacc2021.searchenginebandits.animalqueue.exception.NotFoundException;
import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.model.State;
import com.hacc2021.searchenginebandits.animalqueue.model.StateType;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class QuarantineAdministrationController {
    final QuarantineService quarantineService;

    final StateService stateService;

    @GetMapping("/quarantine/{quarantineId}")
    public String manageQuarantine(@PathVariable("quarantineId") final int quarantineId, final Model model) {
        final Optional<Quarantine> possibleQuarantine = quarantineService.findById(quarantineId);
        if (possibleQuarantine.isEmpty()) {
            throw new NotFoundException("Quarantine not found.");
        }
        final Quarantine quarantine = possibleQuarantine.get();
        model.addAttribute("quarantine", quarantine);
        final List<State> states = quarantine.getStates();
        final State currentState = states.get(states.size() - 1);
        model.addAttribute("currentState", currentState);
        return "manageQuarantine";
    }

    @PostMapping("/quarantine/{quarantineId}")
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
        stateService.addState(quarantine, succeedingState, new StateService.Payload(payloadText, payloadDateTime));

        return manageQuarantine(quarantineId, model);
    }
}
