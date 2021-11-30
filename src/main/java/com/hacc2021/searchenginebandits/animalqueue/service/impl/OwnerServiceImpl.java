package com.hacc2021.searchenginebandits.animalqueue.service.impl;

import com.hacc2021.searchenginebandits.animalqueue.model.Owner;
import com.hacc2021.searchenginebandits.animalqueue.repository.OwnerRepository;
import com.hacc2021.searchenginebandits.animalqueue.service.api.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OwnerServiceImpl implements OwnerService {
    final OwnerRepository ownerRepository;


    @Override
    public void createOwner(final String name, final String emailAddress, final String phoneNumber) {
        final Owner owner = new Owner(name, emailAddress, phoneNumber);
        ownerRepository.save(owner);
        ownerRepository.flush();
    }

    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> findById(final int ownerId) {
        return ownerRepository.findById(ownerId);
    }

    @Override
    public void deleteOwner(final Owner owner) {
        ownerRepository.delete(owner);
    }
}
