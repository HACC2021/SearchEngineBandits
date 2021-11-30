package com.hacc2021.searchenginebandits.animalqueue.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PET")
public class Pet extends AbstractEntity {
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "OWNER_ID")
    private Owner owner;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "CHIP_NO", length = 50)
    private String chipNo;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Quarantine> quarantines = new ArrayList<>();

    public Pet(final Owner owner, final String name, final String chipNo) {
        this.owner = owner;
        this.name = name;
        this.chipNo = chipNo;
    }

    public void addQuarantine(final Quarantine quarantine) {
        quarantines.add(quarantine);
    }
}
