package com.hacc2021.searchenginebandits.animalqueue.service.api;

import com.hacc2021.searchenginebandits.animalqueue.model.Owner;
import com.hacc2021.searchenginebandits.animalqueue.model.Pet;

import java.util.Optional;

public interface PetService {
    Optional<Pet> findById(int petId);

    void createPet(final Owner owner, String name, String chipNo);
}
