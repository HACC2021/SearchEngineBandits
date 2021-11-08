package com.hacc2021.searchenginebandits.animalqueue.service;

import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;

import java.util.Optional;

public interface QuarantineService {
    Optional<Quarantine> getQuarantine(String trackingNo);
}
