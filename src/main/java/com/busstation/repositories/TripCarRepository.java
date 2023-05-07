package com.busstation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busstation.entities.TripCar;
import com.busstation.repositories.mtm.TripCarIdMtm;

@Repository
public interface TripCarRepository extends JpaRepository<TripCar, TripCarIdMtm> {

}
