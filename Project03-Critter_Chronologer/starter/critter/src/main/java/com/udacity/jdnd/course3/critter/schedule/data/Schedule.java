package com.udacity.jdnd.course3.critter.schedule.data;

import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.Employee;
import com.udacity.jdnd.course3.critter.user.data.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    /*
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Customer getCustomer() {
        return customer;
    }

   public void setCustomer(Customer customer) {
        this.customer = customer;
    }



    public List<Pet> getPets() {
        return Pets;
    }

    public void setPets(List<Pet> pets) {
        Pets = pets;
    }
*/
    private LocalDate date;
    @ElementCollection
    private List<Long> employeeIds;
    @ElementCollection
    private List<Long> petIds;
    @ElementCollection
    private Set<EmployeeSkill> activities;

    @ElementCollection
    private Set<Long> customerIds;

    public Set<Long> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(Set<Long> customerIds) {
        this.customerIds = customerIds;
    }

    @ManyToMany
    private List<Employee> employees;

    @ManyToOne
    private Customer customer;

    @ManyToMany
    private List<Pet> Pets;








}
