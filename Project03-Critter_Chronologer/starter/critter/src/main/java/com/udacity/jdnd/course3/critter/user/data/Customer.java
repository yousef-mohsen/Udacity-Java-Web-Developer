package com.udacity.jdnd.course3.critter.user.data;

import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.schedule.data.Schedule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String phoneNumber;
    private String notes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void addPetId(Long id)
    {
        if(petIds == null)
            petIds = new ArrayList<>();
        petIds.add(id);
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    @ElementCollection
    private List<Long> petIds;

    //public List<Pet> getPets() {
      //  return pets;
    //}

    //public void setPets(List<Pet> pets) {
      //  this.pets = pets;
    //}

    public List<Schedule> getSchedules() {
        return Schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        Schedules = schedules;
    }

 //   @OneToMany(mappedBy = "customer")
   // private List<Pet> pets;

    @OneToMany(mappedBy = "customer")
    private List<Schedule> Schedules;


}
