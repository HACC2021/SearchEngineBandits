package com.hacc2021.searchenginebandits.animalqueue.controller;

import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.service.QuarantineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class QuarantineTrackingController {
    final QuarantineService quarantineService;

    @GetMapping("/track")
    public String trackQuarantine(@RequestParam(value = "trackingNo", defaultValue = "") final String trackingNo,
                                  final Model model) {
        model.addAttribute("trackingNo", trackingNo);
        if (trackingNo.isBlank()) {
            return "trackIndex";
        }
        final Optional<Quarantine> quarantine = quarantineService.findByTrackingNo(trackingNo);
        if (quarantine.isEmpty()) {
            return "trackIndex";
        }
        model.addAttribute("quarantine", quarantine.get());
        return "manageQuarantine";
    }
}
