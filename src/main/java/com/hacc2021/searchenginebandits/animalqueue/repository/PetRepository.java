package com.hacc2021.searchenginebandits.animalqueue.repository;

import com.hacc2021.searchenginebandits.animalqueue.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Integer> {

}
