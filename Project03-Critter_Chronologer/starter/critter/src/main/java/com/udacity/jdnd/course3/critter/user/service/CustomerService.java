package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.pet.data.PetRepository;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id){
        return customerRepository.findCustomerById(id);
    }

}
