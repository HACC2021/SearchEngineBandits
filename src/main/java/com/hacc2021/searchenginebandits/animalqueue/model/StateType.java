package com.hacc2021.searchenginebandits.animalqueue.model;

public enum StateType {
    HEALTH_CHECK_PASSED("%s just passed the health check.", 1),
    COLLECTION_TIME_REQUESTED("%s requested to collect %s at %s.", 3),
    COLLECTED("%s was collected ad %s.", 2);

    private final String message;

    private final int numberOfArguments;

    StateType(String message, int numberOfArguments) {
        this.message = message;
        this.numberOfArguments = numberOfArguments;
    }

    public String getMessage(final Object... arguments) {
        if (arguments.length != numberOfArguments)
            throw new IllegalArgumentException("Wrong number of arguments.");
        return String.format(message, arguments);
    }
}
