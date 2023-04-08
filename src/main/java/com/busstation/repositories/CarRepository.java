package com.busstation.repositories;

import com.busstation.entities.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<Car,String> {
    Page<Car> findAllByCarNumber(int carNumber, Pageable pageable);

    List<Car> findByTrips_TripId(String tripId);
}
