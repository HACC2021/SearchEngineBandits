package com.hacc2021.searchenginebandits.animalqueue.repository;

import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuarantineRepository extends JpaRepository<Quarantine, Integer> {

    Quarantine findQuarantineByTrackingNo(final String trackingNo);
}
