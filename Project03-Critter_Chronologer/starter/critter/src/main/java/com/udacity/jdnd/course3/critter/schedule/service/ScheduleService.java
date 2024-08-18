package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.schedule.data.Schedule;
import com.udacity.jdnd.course3.critter.schedule.data.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.data.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;
    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    public Schedule saveSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findSchedulesBypetIds(Long id )
    {
        return scheduleRepository.findSchedulesBypetIds(id);
    }

    public List<Schedule> findSchedulesByemployeeId(Long id )
    {
        return scheduleRepository.findSchedulesByemployeeIds(id);
    }

    public List<Schedule> findSchedulesBycustomerId(Long id )
    {
        return scheduleRepository.findSchedulesBycustomerIds(id);
    }


}
