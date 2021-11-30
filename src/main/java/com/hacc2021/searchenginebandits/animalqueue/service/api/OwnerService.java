package com.hacc2021.searchenginebandits.animalqueue.service.api;

import com.hacc2021.searchenginebandits.animalqueue.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    void createOwner(String name, final String emailAddress, final String phoneNumber);

    List<Owner> findAll();

    Optional<Owner> findById(int ownerId);

    void deleteOwner(Owner owner);
}
