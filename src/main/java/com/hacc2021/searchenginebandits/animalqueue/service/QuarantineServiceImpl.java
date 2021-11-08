package com.hacc2021.searchenginebandits.animalqueue.service;

import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.repository.QuarantineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class QuarantineServiceImpl implements QuarantineService {
    final QuarantineRepository quarantineRepository;


    @Override
    public Optional<Quarantine> findById(final int id) {
        return quarantineRepository.findById(id);
    }

    @Override
    public Optional<Quarantine> findByTrackingNo(final String trackingNo) {
        return quarantineRepository.findByTrackingNo(trackingNo);
    }
}
