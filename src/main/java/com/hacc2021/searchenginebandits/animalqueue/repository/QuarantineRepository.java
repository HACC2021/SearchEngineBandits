package com.hacc2021.searchenginebandits.animalqueue.repository;

import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuarantineRepository extends JpaRepository<Quarantine, Integer> {

    Optional<Quarantine> findByTrackingNo(String trackingNo);
}
