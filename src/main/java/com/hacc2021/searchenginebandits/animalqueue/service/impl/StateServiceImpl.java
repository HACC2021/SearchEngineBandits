package com.hacc2021.searchenginebandits.animalqueue.service.impl;

import com.hacc2021.searchenginebandits.animalqueue.exception.NotAllowedException;
import com.hacc2021.searchenginebandits.animalqueue.model.Pet;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;

    private final NotificationService notificationService;

    private final SpringTemplateEngine templateEngine;


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

        notificationService.sendNotification(quarantine.getPet().getOwner(),
                                             state.getType().getDisplayName(),
                                             constructHtmlMessage(state));
    }

    private boolean isValid(final Quarantine quarantine, final StateType type) {
        if (type == StateType.INITIAL) {
            return quarantine.getStates().isEmpty();
        } else {
            final StateType[] possibleSuccessors = quarantine.getCurrentState().getType().getPossibleSuccessors();
            return Arrays.stream(possibleSuccessors).anyMatch(x -> x == type);
        }
    }

    private String constructHtmlMessage(final State state) {
        final Context context = new Context();
        final Quarantine quarantine = state.getQuarantine();
        context.setVariable("quarantine", quarantine);
        final Pet pet = quarantine.getPet();
        context.setVariable("pet", pet);
        context.setVariable("owner", pet.getOwner());
        context.setVariable("state", state);
        context.setVariable("baseUrl", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());
        return templateEngine.process("email", context);
    }
}
