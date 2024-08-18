package com.udacity.jdnd.course3.critter.user.data;

import com.udacity.jdnd.course3.critter.pet.data.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Transactional
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeById(Long id);
    @Query(value = "SELECT e FROM Employee e JOIN e.skills sk JOIN e.daysAvailable da WHERE sk IN :skillList AND da = :dayOfWeek"
            + " GROUP BY e HAVING COUNT( sk) = :skillListSize")
    List<Employee> findAllBySkillsAndDaysAvailability(@Param("skillList") Set<EmployeeSkill> skills,DayOfWeek dayOfWeek,
                                @Param("skillListSize") long skillListSize);

}
