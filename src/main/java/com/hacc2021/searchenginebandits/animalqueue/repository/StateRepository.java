package com.hacc2021.searchenginebandits.animalqueue.repository;

import com.hacc2021.searchenginebandits.animalqueue.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Integer> {

}
