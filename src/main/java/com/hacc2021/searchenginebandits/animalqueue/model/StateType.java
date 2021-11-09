package com.hacc2021.searchenginebandits.animalqueue.model;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

@Getter
public enum StateType {
    COLLECTED("Pet collected",
              (owner, pet, quarantine, state) -> String.format("%s was collected.", pet.getName()),
              null,
              null,
              100),
    READY_FOR_COLLECTION("Ready for collection",
                         ((owner, pet, quarantine, state) -> String.format("%s is ready for collection.",
                                                                           pet.getName())),
                         null,
                         null,
                         85,
                         COLLECTED),
    COLLECTION_TIME_CONFIRMED("Collection time confirmed",
                              ((owner, pet, quarantine, state) -> String.format(
                                      "The collection time for %s on %tD at %s was confirmed.",
                                      pet.getName(),
                                      state.getPayloadDateTime(),
                                      state.getPayloadDateTime().format(Constants.TIME_FORMATTER))),
                              null,
                              "Confirmed collection time",
                              70,
                              READY_FOR_COLLECTION),
    COLLECTION_TIME_REQUESTED("Collection time requested",
                              (owner, pet, quarantine, state) -> String.format(
                                      "%s requested to collect %s on %tD at %s.",
                                      owner.getName(),
                                      pet.getName(),
                                      state.getPayloadDateTime(),
                                      state.getPayloadDateTime().format(Constants.TIME_FORMATTER)),
                              null,
                              "Requested collection time",
                              55,
                              COLLECTION_TIME_CONFIRMED),
    COLLECTION_TIME_REQUESTABLE("Collection time may be requested",
                                (owner, pet, quarantine, state) -> String.format(
                                        "%s can request a collection time for %s now.",
                                        owner.getName(),
                                        pet.getName()),
                                null,
                                null,
                                35,
                                COLLECTION_TIME_REQUESTED),
    HEALTH_CHECK_PASSED("Health check passed",
                        (owner, pet, quarantine, state) -> String.format("%s passed the health check.", pet.getName()),
                        null,
                        null,
                        25,
                        COLLECTION_TIME_REQUESTABLE),

    INITIAL("Quarantine created",
            (owner, pet, quarantine, state) -> String.format("Quarantine of %s has been created in the system.",
                                                             pet.getName()),
            null,
            null,
            10,
            HEALTH_CHECK_PASSED);

    private final String displayName;

    private final MessageSupplier messageSupplier;

    private final String payloadTextName;

    private final String payloadDateTimeName;

    @Accessors(fluent = true)
    private final boolean hasPayloadText;

    @Accessors(fluent = true)
    private final boolean hasPayloadDateTime;

    private final StateType[] possibleSuccessors;

    private final int progress;

    StateType(final String displayName,
              final MessageSupplier messageSupplier,
              final String payloadTextName,
              final String payloadDateTimeName,
              final int progress,
              final StateType... possibleSuccessors) {
        this.displayName = displayName;
        this.messageSupplier = messageSupplier;
        this.payloadTextName = payloadTextName;
        this.hasPayloadText = payloadTextName != null && !payloadTextName.isBlank();
        this.payloadDateTimeName = payloadDateTimeName;
        this.hasPayloadDateTime = payloadDateTimeName != null && !payloadDateTimeName.isBlank();
        this.progress = progress;
        this.possibleSuccessors = possibleSuccessors;
    }

    public String createMessage(final State state) {
        return messageSupplier.supply(state);
    }

    public StateType[] getPossibleSuccessors() {
        return Arrays.copyOf(possibleSuccessors, possibleSuccessors.length);
    }

    @FunctionalInterface
    interface MessageSupplier {
        default String supply(final State state) {
            final Quarantine quarantine = state.getQuarantine();
            final Pet pet = quarantine.getPet();
            final Owner owner = pet.getOwner();
            return supply(owner, pet, quarantine, state);
        }

        String supply(Owner owner, Pet pet, Quarantine quarantine, State state);
    }

    private static class Constants {
        protected static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);
    }
}
