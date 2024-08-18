package com.udacity.jdnd.course3.critter.pet.controller;

import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.pet.data.PetDTO;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;
    ModelMapper modelMapper = new ModelMapper();
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.savePet(convertPetDTOToPet(petDTO));
        Customer owner = customerService.findCustomerById(petDTO.getOwnerId());
        owner.addPetId(pet.getId());
        //owner.
        return convertPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDTO(petService.findPetsByid(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDtoList = Arrays.asList(modelMapper.map(petService.findAll(), PetDTO[].class));
        return petDtoList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDtoList = Arrays.asList(modelMapper.map(petService.findPetsByownerId(ownerId), PetDTO[].class));
        return petDtoList;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }


}
