package com.udacity.jdnd.course3.critter.pet.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PetRepository extends JpaRepository <Pet, Long> {

    Pet findPetsById(Long id);
    List<Pet> findPetsByownerId (Long id);
}
