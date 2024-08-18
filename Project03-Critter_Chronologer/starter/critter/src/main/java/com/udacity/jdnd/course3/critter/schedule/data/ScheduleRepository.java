package com.udacity.jdnd.course3.critter.schedule.data;

import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.Employee;
import com.udacity.jdnd.course3.critter.user.data.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.List;
@Transactional
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findSchedulesBypetIds(Long id);
    List<Schedule> findSchedulesByemployeeIds(Long id);

    List<Schedule> findSchedulesBycustomerIds(Long id);

}
