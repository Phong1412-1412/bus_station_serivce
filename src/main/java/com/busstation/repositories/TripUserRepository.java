package com.busstation.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.busstation.entities.TripUser;
import com.busstation.entities.User;
import com.busstation.repositories.mtm.TripUserIdMtm;

@Repository
public interface TripUserRepository extends JpaRepository<TripUser, TripUserIdMtm> {
	
    TripUser findTripUserByUserId(String userId);

    @Query("SELECT u from User u, Car c, Employee e where e.car.carId = c.carId and e.user.userId = u.userId and c.carId = ?1")
    Optional<User> findInforEmployeeByCarId(String cartId);
}

