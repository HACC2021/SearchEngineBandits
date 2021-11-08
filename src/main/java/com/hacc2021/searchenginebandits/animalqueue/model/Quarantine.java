package com.hacc2021.searchenginebandits.animalqueue.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "QUARANTINE")
public class Quarantine extends AbstractEntity {
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "PET_ID")
    private Pet pet;

    @Column(name = "TRACKING_NO", length = 20, nullable = false)
    private String trackingNo;

    @Column(name = "CREATION", nullable = false)
    private LocalDateTime creation;

    @Column(name = "END", nullable = false)
    private LocalDateTime end;

    @OneToMany(mappedBy = "quarantine")
    private List<State> states = new ArrayList<>();

    public Quarantine(final Pet pet, final String trackingNo, final LocalDateTime creation) {
        this.pet = pet;
        this.trackingNo = trackingNo;
        this.creation = creation;
    }
}
