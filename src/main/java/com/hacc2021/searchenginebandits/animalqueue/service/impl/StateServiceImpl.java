package com.hacc2021.searchenginebandits.animalqueue.service.impl;

import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.model.State;
import com.hacc2021.searchenginebandits.animalqueue.model.StateType;
import com.hacc2021.searchenginebandits.animalqueue.repository.StateRepository;
import com.hacc2021.searchenginebandits.animalqueue.service.api.NotificationService;
import com.hacc2021.searchenginebandits.animalqueue.service.api.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StateServiceImpl implements StateService {
    final StateRepository stateRepository;

    final NotificationService notificationService;


    @Override
    public void addState(final Quarantine quarantine, final StateType type, final Payload payload) {
        final State state = new State(quarantine, type, LocalDateTime.now());
        if (payload.hasText()) {
            state.setPayloadText(payload.getText());
        }
        if (payload.hasDateTime()) {
            state.setPayloadDateTime(payload.getDateTime());
        }
        quarantine.addState(state);
        stateRepository.save(state);
        stateRepository.flush();

        notificationService.sendNotification(state.getMessage());
    }
}
