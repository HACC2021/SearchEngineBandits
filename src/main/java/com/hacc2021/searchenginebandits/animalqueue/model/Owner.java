package com.hacc2021.searchenginebandits.animalqueue.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "OWNER")
public class Owner extends AbstractEntity {
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "EMAIL_ADDRESS", length = 100)
    private String emailAddress;

    @Column(name = "PHONE_NUMBER", length = 50)
    private String phoneNumber;

    @OneToMany(mappedBy = "owner")
    private List<Pet> pets = new ArrayList<>();

    public Owner(final String name, final String emailAddress, final String phoneNumber) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
}
