package com.busstation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busstation.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

    List<Location> findAllByProvince_ProvinceId(int provinceId);

    Location findByName(String nameLocation);
}
