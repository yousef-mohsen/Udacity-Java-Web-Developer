package com.udacity.jdnd.course3.critter.schedule.controller;

import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.data.Schedule;
import com.udacity.jdnd.course3.critter.schedule.data.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        //got nested exception by using bean.utils.copy
        Schedule temp = modelMapper.map(scheduleDTO, Schedule.class);

        Set<Long> customerIds = new HashSet<Long>();
        for(Long petid : scheduleDTO.getPetIds())
        {
            customerIds.add(petService.findPetsByid(petid).getOwnerId());
        }
        temp.setCustomerIds(customerIds);


        Schedule temp2 = scheduleService.saveSchedule(temp);
        return modelMapper.map(temp2, ScheduleDTO.class);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<ScheduleDTO> scheduleDtoList = Arrays.asList(modelMapper.map(scheduleService.findAll(), ScheduleDTO[].class));
        return scheduleDtoList;
    }


    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDtoList = Arrays.asList(modelMapper.map(scheduleService.findSchedulesBypetIds(petId), ScheduleDTO[].class));
        return scheduleDtoList;

    }


    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDtoList = Arrays.asList(modelMapper.map(scheduleService.findSchedulesByemployeeId(employeeId), ScheduleDTO[].class));
        return scheduleDtoList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDtoList = Arrays.asList(modelMapper.map(scheduleService.findSchedulesBycustomerId(customerId), ScheduleDTO[].class));
        return scheduleDtoList;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }



}

