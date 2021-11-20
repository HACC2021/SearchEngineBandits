package com.hacc2021.searchenginebandits.animalqueue.service.impl;

import com.hacc2021.searchenginebandits.animalqueue.exception.NotAllowedException;
import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.model.State;
import com.hacc2021.searchenginebandits.animalqueue.model.StateType;
import com.hacc2021.searchenginebandits.animalqueue.repository.StateRepository;
import com.hacc2021.searchenginebandits.animalqueue.service.api.NotificationService;
import com.hacc2021.searchenginebandits.animalqueue.service.api.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class StateServiceImpl implements StateService {
    final StateRepository stateRepository;

    final NotificationService notificationService;


    @Override
    public void addState(final Quarantine quarantine, final StateType type, final Payload payload) {
        if (!isValid(quarantine, type)) {
            throw new NotAllowedException(String.format("The specified type '%s' is not allowed here.", type));
        }
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

    private boolean isValid(final Quarantine quarantine, final StateType type) {
        if (type == StateType.INITIAL) {
            return quarantine.getStates().isEmpty();
        } else {
            final StateType[] possibleSuccessors = quarantine.getCurrentState().getType().getPossibleSuccessors();
            return Arrays.stream(possibleSuccessors).anyMatch(x -> x == type);
        }
    }
}
