package com.hacc2021.searchenginebandits.animalqueue.model;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;

@Getter
public enum StateType {
    COLLECTED("Pet collected",
              (owner, pet, quarantine, state) -> String.format("%s was collected.", pet.getName()),
              null,
              null),
    COLLECTION_TIME_REQUESTED("Collection time requested",
                              (owner, pet, quarantine, state) -> String.format(
                                      "%s requested to collect %s on %tD at %tR.",
                                      owner.getName(),
                                      pet.getName(),
                                      state.getPayloadDateTime(),
                                      state.getPayloadDateTime()),
                              null,
                              "Requested collection time",
                              COLLECTED),
    TEMPORARY_SAMPLE_TYPE("Temporary sample type",
                          (owner, pet, quarantine, state) -> String.format("%s samples with %s on %tD at %tR.",
                                                                           owner.getName(),
                                                                           pet.getName(),
                                                                           state.getPayloadDateTime(),
                                                                           state.getPayloadDateTime()),
                          "Payload text sample",
                          "Payload date time sample",
                          COLLECTED),
    HEALTH_CHECK_PASSED("Health check passed",
                        (owner, pet, quarantine, state) -> String.format("%s passed the health check.", pet.getName()),
                        null,
                        null,
                        COLLECTION_TIME_REQUESTED,
                        TEMPORARY_SAMPLE_TYPE),

    INITIAL("Quarantine created",
            (owner, pet, quarantine, state) -> String.format("Quarantine of %s has been created in the system.",
                                                             pet.getName()),
            null, null, HEALTH_CHECK_PASSED);

    private final String displayName;

    private final MessageSupplier messageSupplier;

    private final String payloadTextName;

    private final String payloadDateTimeName;

    @Accessors(fluent = true)
    private final boolean hasPayloadText;

    @Accessors(fluent = true)
    private final boolean hasPayloadDateTime;

    private final StateType[] possibleSuccessors;

    StateType(final String displayName, final MessageSupplier messageSupplier, final String payloadTextName,
              final String payloadDateTimeName,
              final StateType... possibleSuccessors) {
        this.displayName = displayName;
        this.messageSupplier = messageSupplier;
        this.payloadTextName = payloadTextName;
        this.hasPayloadText = payloadTextName != null && !payloadTextName.isBlank();
        this.payloadDateTimeName = payloadDateTimeName;
        this.hasPayloadDateTime = payloadDateTimeName != null && !payloadDateTimeName.isBlank();
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
}
