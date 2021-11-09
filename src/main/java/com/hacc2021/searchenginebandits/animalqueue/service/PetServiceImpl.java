package com.hacc2021.searchenginebandits.animalqueue.service;

import com.hacc2021.searchenginebandits.animalqueue.model.Owner;
import com.hacc2021.searchenginebandits.animalqueue.model.Pet;
import com.hacc2021.searchenginebandits.animalqueue.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PetServiceImpl implements PetService {
    final PetRepository petRepository;

    @Override
    public Optional<Pet> findById(final int petId) {
        return petRepository.findById(petId);
    }

    @Override
    public void createPet(final Owner owner, final String name, final String chipNo) {
        final Pet pet = new Pet(owner, name, chipNo);
        owner.addPet(pet);
        petRepository.save(pet);
        petRepository.flush();
    }
}
