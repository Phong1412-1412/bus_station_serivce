package com.busstation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.busstation.entities.Car;
import com.busstation.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{

    List<Order> findAllByTrip_TripId(String tripId);

    @Query("SELECT COUNT(o) FROM Order o "
    	       + "WHERE EXTRACT(MONTH FROM o.createAt) = ?1 "
    	       + "AND EXTRACT(YEAR FROM o.createAt) = ?2")
    Integer countOrdersByMonthAndYear(Integer month, Integer year);

    @Query("SELECT c FROM Order o" +
            " INNER JOIN TripCar tc ON tc.tripId = o.trip.tripId" +
            " INNER JOIN Car c ON c.carId = tc.carId" +
            " INNER JOIN User u ON u.userId = o.user.userId" +
            " WHERE o.orderID = :orderId " +
            " AND u.userId = :userId")
    Car findByOrderIdAndUserId(@Param("orderId") String orderId, @Param("userId") String userId);
    
}
