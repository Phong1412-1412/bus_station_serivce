package com.busstation.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busstation.entities.Car;
import com.busstation.entities.Chair;


@Repository
public interface ChairRepository extends JpaRepository<Chair, String> {


    Page<Chair> findAllByCar(Car car, Pageable pageable);

    List<Chair> findAllByCar(Car car);
    Chair findAllByCarAndChairNumber(Car car, int chairNumber);

}
