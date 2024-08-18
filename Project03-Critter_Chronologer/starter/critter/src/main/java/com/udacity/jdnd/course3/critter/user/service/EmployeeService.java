package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.user.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee findEmployeeByid(Long id)
    {
        return employeeRepository.findEmployeeById(id);
    }

    public List<Employee> findByDateIndaysAvailableAndSkillInSkills(DayOfWeek day, Set<EmployeeSkill> skill)
    {

        return employeeRepository.findAllBySkillsAndDaysAvailability(skill, day, skill.size());
    }

}
