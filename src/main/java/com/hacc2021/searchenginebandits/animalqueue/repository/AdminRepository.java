package com.hacc2021.searchenginebandits.animalqueue.repository;

import com.hacc2021.searchenginebandits.animalqueue.model.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Integer>{

    public Optional<Admin> findByUserName(@Param("USERNAME") final String userName);
}
