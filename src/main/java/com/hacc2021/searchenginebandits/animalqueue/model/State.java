package com.hacc2021.searchenginebandits.animalqueue.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "STATE")
public class State extends AbstractEntity {
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "QUARANTINE_ID")
    private Quarantine quarantine;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 50, nullable = false)
    private StateType type;

    @Column(name = "CREATION", nullable = false)
    private LocalDateTime creation;

    @Column(name = "PAYLOAD_TEXT")
    private String payloadText;

    @Column(name = "PAYLOAD_DATETIME")
    private LocalDateTime payloadDateTime;

    public State(final Quarantine quarantine, final StateType type, final LocalDateTime creation) {
        this.quarantine = quarantine;
        this.type = type;
        this.creation = creation;
    }

    public String getMessage() {
        return type.createMessage(this);
    }

}
