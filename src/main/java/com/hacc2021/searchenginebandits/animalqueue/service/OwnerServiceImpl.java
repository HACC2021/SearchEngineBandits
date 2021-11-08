package com.hacc2021.searchenginebandits.animalqueue.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hacc2021.searchenginebandits.animalqueue.repository.OwnerRepository;
import com.hacc2021.searchenginebandits.animalqueue.model.Owner;

@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {
    final OwnerRepository ownerRepository;

    public OwnerServiceImpl(@Autowired final OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void createOwner(final String name) {
        final Owner owner = new Owner(name, "something@somewhere.com", "123");
        ownerRepository.save(owner);
        ownerRepository.flush();
    }

    @Override
    public List<Owner> listOwners() {
        return ownerRepository.findAll();
    }
}
