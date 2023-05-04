package com.busstation.repositories;

import com.busstation.entities.Employee;
import com.busstation.entities.TripUser;
import com.busstation.entities.User;
import com.busstation.repositories.mtm.TripUserIdMtm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripUserRepository extends JpaRepository<TripUser, TripUserIdMtm> {
    TripUser findTripUserByUserId(String userId);

    @Query("SELECT u FROM Order o" +
            " INNER JOIN TripCar tc ON tc.tripId = o.trip.tripId" +
            " INNER JOIN Car c ON c.carId = tc.carId" +
            " INNER JOIN Employee e ON  e.car.carId = c.carId" +
            " INNER JOIN User u ON u.userId = e.user.userId" +
            " WHERE o.orderID = :orderId")
    Optional<User> findEmployeeByOderAndCarAndUser(@Param("orderId") String orderId);
}

