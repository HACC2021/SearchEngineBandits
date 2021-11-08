package com.hacc2021.searchenginebandits.animalqueue.model;

import lombok.AllArgsConstructor;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    private Owner owner;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "CHIP_NO", length = 50)
    private String chipNo;

    @OneToMany(mappedBy = "pet")
    private List<Quarantine> quarantines = new ArrayList<>();

    public Pet(Owner owner, String name, String chipNo) {
        this.owner = owner;
        this.name = name;
        this.chipNo = chipNo;
    }
}
