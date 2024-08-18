package com.udacity.jdnd.course3.critter.user.data;

import com.udacity.jdnd.course3.critter.schedule.data.Schedule;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private long id;
    private String name;


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

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }


    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }


    @ElementCollection
    private Set<EmployeeSkill> skills;
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

    public List<Schedule> getSchedules() {
        return Schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        Schedules = schedules;
    }

    @ManyToMany
    private List<Schedule> Schedules;



}
