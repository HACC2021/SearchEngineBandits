package com.hacc2021.searchenginebandits.animalqueue.service.api;

import com.hacc2021.searchenginebandits.animalqueue.model.Pet;
import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;

import java.util.Optional;

public interface QuarantineService {
    Optional<Quarantine> findById(int id);

    Optional<Quarantine> findByTrackingNo(String trackingNo);

    void createQuarantine(Pet pet);

    void endQuarantine(Quarantine quarantine);

    void deleteQuarantine(Quarantine quarantine);
}
