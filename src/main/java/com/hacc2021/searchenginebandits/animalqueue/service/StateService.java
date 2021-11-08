package com.hacc2021.searchenginebandits.animalqueue.service;

import com.hacc2021.searchenginebandits.animalqueue.model.Quarantine;
import com.hacc2021.searchenginebandits.animalqueue.model.StateType;
import lombok.Getter;

import java.time.LocalDateTime;

public interface StateService {

    void addState(Quarantine quarantine, StateType type, Payload payload);

    @Getter
    class Payload {
        private String text;

        private LocalDateTime dateTime;

        public Payload() {
            this(null, null);
        }

        public Payload(final String text) {
            this(text, null);
        }

        public Payload(final LocalDateTime dateTime) {
            this(null, dateTime);
        }

        public Payload(final String text, final LocalDateTime dateTime) {
            this.text = text;
            this.dateTime = dateTime;
        }

        boolean hasText() {
            return text != null;
        }

        boolean hasDateTime() {
            return dateTime != null;
        }

        public static Payload empty() {
            return new Payload();
        }
    }
}
