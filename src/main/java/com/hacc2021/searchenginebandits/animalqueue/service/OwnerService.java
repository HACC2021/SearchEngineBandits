package com.hacc2021.searchenginebandits.animalqueue.service;

import java.util.List;
import com.hacc2021.searchenginebandits.animalqueue.model.Owner;

public interface OwnerService {
    void createOwner(String name);

    List<Owner> listOwners();
}
