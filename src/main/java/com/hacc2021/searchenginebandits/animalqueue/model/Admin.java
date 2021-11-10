package com.hacc2021.searchenginebandits.animalqueue.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ADMIN")
public class Admin extends AbstractEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "USERNAME", length = 50)
    private String userName;

    @Column(name = "PASSWORD", length = 50)
    private String password;

    @Column(name = "ROLE", length = 20)
    private String role;


    public Admin(final String userName, final String password, final String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
}
