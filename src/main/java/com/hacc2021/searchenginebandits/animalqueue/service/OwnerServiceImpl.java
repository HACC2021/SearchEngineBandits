package com.hacc2021.searchenginebandits.animalqueue.service;

import com.hacc2021.searchenginebandits.animalqueue.model.Owner;
import com.hacc2021.searchenginebandits.animalqueue.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OwnerServiceImpl implements OwnerService {
    final OwnerRepository ownerRepository;


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
