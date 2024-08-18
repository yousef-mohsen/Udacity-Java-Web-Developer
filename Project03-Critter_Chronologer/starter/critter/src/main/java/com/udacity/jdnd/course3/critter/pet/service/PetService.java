package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.pet.data.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public Pet savePet(Pet pet){
        return petRepository.save(pet);
    }

    public List<Pet> findAll(){
        return petRepository.findAll();
    }


    public Pet findPetsByid(Long id)
    {
        return petRepository.findPetsById(id);
    }
    public List<Pet> findPetsByownerId (Long id)
    {
        return petRepository.findPetsByownerId(id);
    }
}
