package com.busstation.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.busstation.entities.Car;
import com.busstation.entities.Chair;

@Repository
public interface ChairRepository extends JpaRepository<Chair, String> {

	Page<Chair> findAllByCar(Car car, Pageable pageable);

	List<Chair> findAllByCar(Car car);

	Chair findAllByCarAndChairNumber(Car car, int chairNumber);
	
	@Query("SELECT COUNT(ch) FROM Chair ch INNER JOIN ch.car c WHERE c.carId = ?1")
	int countChairsByCarId(String carId);

	@Query("SELECT COUNT(od) " + "FROM OrderDetail od " + "JOIN od.order o "
			+ "JOIN od.chair ch " + "JOIN ch.car c " + "JOIN o.trip tr "
			+ "WHERE tr.tripId = ?1 AND c.carId = ?2")
	int countSoldSeatsByTripIdAndCarNumber(String tripId, String carId);

}
