package com.hacc2021.searchenginebandits.animalqueue.service;

import com.hacc2021.searchenginebandits.animalqueue.model.Pet;
import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.model.StateType;
import com.hacc2021.searchenginebandits.animalqueue.repository.QuarantineRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class QuarantineServiceImpl implements QuarantineService {
    final QuarantineRepository quarantineRepository;

    final StateService stateService;

    @Override
    public Optional<Quarantine> findById(final int id) {
        return quarantineRepository.findById(id);
    }

    @Override
    public Optional<Quarantine> findByTrackingNo(final String trackingNo) {
        return quarantineRepository.findByTrackingNo(trackingNo);
    }

    @Override
    public void createQuarantine(final Pet pet) {
        final String trackingNo = createTrackingNo(pet);

        final Quarantine quarantine = new Quarantine(pet, trackingNo, LocalDateTime.now());
        pet.addQuarantine(quarantine);
        quarantineRepository.save(quarantine);
        quarantineRepository.flush();
        stateService.addState(quarantine, StateType.INITIAL, StateService.Payload.empty());
    }

    @Override
    public void endQuarantine(final Quarantine quarantine) {
        quarantine.setEnd(LocalDateTime.now());
        quarantineRepository.save(quarantine);
        quarantineRepository.flush();
    }

    private String createTrackingNo(final Pet pet) {
        int i = 0;
        while (true) {
            final String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            final String hashBase = pet.getChipNo() + pet.getId() + now + i++;
            final byte[] baseBytes = hashBase.getBytes(StandardCharsets.UTF_8);
            final byte[] hash = DigestUtils.md5Digest(baseBytes);
            final String trackingNo = new Base32().encodeAsString(hash).substring(0, 16);
            if (findByTrackingNo(trackingNo).isEmpty()) {
                return trackingNo;
            }
        }
    }
}
