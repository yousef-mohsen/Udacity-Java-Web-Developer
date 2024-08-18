package com.udacity.jdnd.course3.critter.user.controller;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.pet.data.PetDTO;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.data.*;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    CustomerService customerService;


    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return convertCustomerToCustomerDTO(customerService.saveCustomer(convertCustomerDTOToCustomer(customerDTO)));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDtoList = Arrays.asList(modelMapper.map(customerService.findAll(), CustomerDTO[].class));
        return customerDtoList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.findPetsByid(petId);
        return convertCustomerToCustomerDTO(customerService.findCustomerById(pet.getOwnerId()));

    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return convertEmployeeToEmployeeDTO(employeeService.saveEmployee(convertEmployeeDTOToEmployee(employeeDTO)));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        return convertEmployeeToEmployeeDTO(employeeService.findEmployeeByid(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.findEmployeeByid(employeeId).setDaysAvailable(daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

       List<Employee> employees = employeeService.findByDateIndaysAvailableAndSkillInSkills(employeeDTO.getDate().getDayOfWeek(), employeeDTO.getSkills());
        List<EmployeeDTO> employeeDtoList = Arrays.asList(modelMapper.map(employees, EmployeeDTO[].class));
        return employeeDtoList;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }




}
