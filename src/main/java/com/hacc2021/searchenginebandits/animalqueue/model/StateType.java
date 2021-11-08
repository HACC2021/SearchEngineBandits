package com.hacc2021.searchenginebandits.animalqueue.model;

public enum StateType {
    HEALTH_CHECK_PASSED((owner, pet, quarantine, state) -> String.format("%s passed the health check.", pet.getName())),
    COLLECTION_TIME_REQUESTED((owner, pet, quarantine, state) -> String.format(
            "%s requested to collect %s on %tD at %tR.",
            owner.getName(),
            pet.getName(),
            state.getPayloadDateTime(),
            state.getPayloadDateTime())),
    COLLECTED((owner, pet, quarantine, state) -> String.format("%s was collected.", pet.getName()));

    private final MessageSupplier messageSupplier;

    StateType(final MessageSupplier messageSupplier) {
        this.messageSupplier = messageSupplier;
    }

    public String createMessage(final State state) {
        return messageSupplier.supply(state);
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
