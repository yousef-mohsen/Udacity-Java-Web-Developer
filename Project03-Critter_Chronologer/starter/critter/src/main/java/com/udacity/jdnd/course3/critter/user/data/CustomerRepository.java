package com.udacity.jdnd.course3.critter.user.data;

import com.udacity.jdnd.course3.critter.pet.data.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerById (Long id);
}
