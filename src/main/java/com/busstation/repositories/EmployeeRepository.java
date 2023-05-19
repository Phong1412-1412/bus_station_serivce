package com.busstation.repositories;

import com.busstation.entities.Employee;
import com.busstation.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {

    Employee findByUser(User userId);

    Page<Employee> findAllByCarIsNotNull(Pageable pageable);

    @Query("SELECT e FROM Employee e " +
            " INNER JOIN Car c on c.carId = e.car.carId " +
            " WHERE c.carId = :carId")
    Optional<Employee> findEmployeeByCarId(@Param("carId") String carId);
}
