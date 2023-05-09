package com.busstation.repositories;

import com.busstation.entities.Car;
import com.busstation.entities.TypeCar;
import com.busstation.payload.response.CarResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TypeCarRepository extends JpaRepository<TypeCar, Integer> {

    Optional<TypeCar> findByTypeCarName(String typeCarName);

    Optional<TypeCar> findByTypeCarId(int typeCarId);

    @Query("SELECT tc FROM Car c inner join TypeCar tc on tc.typeCarId = c.typeCar.typeCarId where c.carId = :carId")

    TypeCar getTypeCarByCarId(@Param("carId") String carId);

    @Query("SELECT c from Car c " +
            " INNER join TypeCar tc on tc.typeCarId = c.typeCar.typeCarId" +
            " where tc.typeCarId = :typeCarId")
    List<Car> getCarsByTypeCar(@Param("typeCarId") int typeCarId);
}
