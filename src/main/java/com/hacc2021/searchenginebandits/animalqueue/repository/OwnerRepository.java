package com.hacc2021.searchenginebandits.animalqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hacc2021.searchenginebandits.animalqueue.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}
