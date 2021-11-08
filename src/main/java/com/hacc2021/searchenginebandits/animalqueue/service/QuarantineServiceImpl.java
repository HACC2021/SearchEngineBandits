package com.hacc2021.searchenginebandits.animalqueue.service;

import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.repository.QuarantineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class QuarantineServiceImpl implements QuarantineService {
    final QuarantineRepository quarantineRepository;

    public QuarantineServiceImpl(@Autowired final QuarantineRepository quarantineRepository) {
        this.quarantineRepository = quarantineRepository;
    }

    @Override
    public Optional<Quarantine> getQuarantine(final String trackingNo) {
        final Quarantine quarantine = quarantineRepository.findQuarantineByTrackingNo(trackingNo);
        return Optional.ofNullable(quarantine);
    }
}
