package com.busstation.repositories;

import java.time.LocalDateTime;
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

    @Query("SELECT DISTINCT c FROM Order o " +
            " INNER JOIN OrderDetail od ON od.order.orderID = o.orderID " +
    		" INNER JOIN Chair ch ON ch.chairId = od.chair.chairId "+
            " INNER JOIN Car c ON c.carId = ch.car.carId " +
            " INNER JOIN User u ON u.userId = o.user.userId " +
            " WHERE o.orderID = :orderId " +
            " AND u.userId = :userId")
    Car findByOrderIdAndUserId(@Param("orderId") String orderId, @Param("userId") String userId);

    @Query("SELECT o FROM Order o inner join Trip t on o.trip.tripId = t.tripId WHERE t.timeStart >= :startTime AND t.timeStart <= :endTime")
    List<Order> findOrderByTripTime(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
