package com.hacc2021.searchenginebandits.animalqueue.model;

import lombok.AllArgsConstructor;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "PET_ID")
    private Pet pet;

    @Column(name = "CREATION", nullable = false)
    private LocalDateTime creation;

    @Column(name = "CREATION", nullable = false)
    private LocalDateTime end;

    @OneToMany(mappedBy = "pet")
    private List<State> states = new ArrayList<>();

    public Quarantine(Pet pet, LocalDateTime creation) {
        this.pet = pet;
        this.creation = creation;
    }
}
