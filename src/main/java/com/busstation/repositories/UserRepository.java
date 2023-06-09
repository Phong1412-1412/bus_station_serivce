package com.busstation.repositories;

import com.busstation.entities.Order;
import com.busstation.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT user FROM User user WHERE user.email LIKE %:keyword% or user.phoneNumber  LIKE %:keyword%")
    Page<User> findAllByEmailOrPhoneNumber(@Param("keyword") String keyword, Pageable pageable);

    Page<User> findByTrips_TripId(String tripId, Pageable pageable);

    List<User> findAllByTrips_TripId(String tripId);

    Boolean existsByEmail(String email);

    User findUserByOrders(Order order);

    @Query("SELECT u FROM User u JOIN Order o ON o.user.userId = u.userId WHERE o.orderID = :orderID")
    User findUserByOrderID(@Param("orderID") String orderID);

    @Query("SELECT DISTINCT u FROM User u "
            + "INNER JOIN Order o ON o.user.userId = u.userId "
            + "INNER JOIN Trip t ON t.tripId = o.trip.tripId "
            + "WHERE t.tripId = :tripId")
    List<User> findUsersByTripId(@Param("tripId")String tripId);

    @Query("select u from User u where u.account.accountId = ?1")
	User findByAccountId(String accountId);

    @Query("select u from User u where u.email = ?1")
	User findByEmail(String email);

    @Query("select u from User u where u.email = ?1 and u.authProvider='GOOGLE'")
	User findByEmailAndProvider(String email);
}
