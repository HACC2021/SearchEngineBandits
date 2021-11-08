package com.hacc2021.searchenginebandits.animalqueue.controller;

import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.service.QuarantineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor
public class QuarantineTrackingController {
    @Autowired
    final QuarantineService quarantineService;

    @GetMapping("/track")
    public String trackIndex(final Model model) {
        model.addAttribute("trackingNo", "");
        return "trackIndex";
    }

    @GetMapping("/track/{trackingNo}")
    public String trackQuarantine(@PathVariable("trackingNo") final String trackingNo, final Model model) {
        final Optional<Quarantine> quarantine = quarantineService.getQuarantine(trackingNo);
        if (quarantine.isPresent()) {
            model.addAttribute("quarantine", quarantine.get());
            return "trackQuarantine";
        }
        model.addAttribute("trackingNo", trackingNo);
        return "trackIndex";
    }
}
