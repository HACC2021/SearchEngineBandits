package com.hacc2021.searchenginebandits.animalqueue.controller;

import com.hacc2021.searchenginebandits.animalqueue.exception.NotAllowedException;
import com.hacc2021.searchenginebandits.animalqueue.exception.NotFoundException;
import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.model.StateType;
import com.hacc2021.searchenginebandits.animalqueue.service.api.QuarantineService;
import com.hacc2021.searchenginebandits.animalqueue.service.api.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class QuarantineTrackingController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm");

    private final QuarantineService quarantineService;

    private final StateService stateService;

    @GetMapping("/track")
    public String trackQuarantine(@RequestParam(value = "trackingNo", defaultValue = "") final String trackingNo,
                                  final Model model) {
        model.addAttribute("trackingNo", trackingNo);
        if (trackingNo.isBlank()) {
            return "trackIndex";
        }
        final Optional<Quarantine> possibleQuarantine = quarantineService.findByTrackingNo(trackingNo);
        if (possibleQuarantine.isEmpty()) {
            return "trackIndex";
        }
        final Quarantine quarantine = possibleQuarantine.get();
        model.addAttribute("quarantine", quarantine);
        model.addAttribute("collectionTimeRequestable",
                           quarantine.getCurrentState().getType() == StateType.COLLECTION_TIME_REQUESTABLE);
        final LocalDateTime min = LocalDateTime.now().plusHours(3).withSecond(0).withMinute(0);
        model.addAttribute("min", min.format(FORMATTER));
        model.addAttribute("max", min.plusDays(5).format(FORMATTER));
        return "trackQuarantine";
    }

    @PostMapping("/requestCollectionTime")
    public String requestCollectionTime(@RequestParam(value = "trackingNo", defaultValue = "") final String trackingNo,
                                        @RequestParam(value = "requestedCollectionTime")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        final LocalDateTime requestedDateTime) {
        final Optional<Quarantine> possibleQuarantine = quarantineService.findByTrackingNo(trackingNo);
        if (possibleQuarantine.isEmpty()) {
            throw new NotFoundException("Quarantine not found.");
        }
        final Quarantine quarantine = possibleQuarantine.get();
        if (quarantine.getCurrentState().getType() != StateType.COLLECTION_TIME_REQUESTABLE) {
            throw new NotAllowedException("Collection time may not be requested right now.");
        }
        stateService.addState(quarantine,
                              StateType.COLLECTION_TIME_REQUESTED,
                              new StateService.Payload(null, requestedDateTime));
        return "redirect:/track?trackingNo=" + trackingNo;
    }
}
