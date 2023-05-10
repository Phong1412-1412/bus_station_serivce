package com.busstation.services;

import com.busstation.entities.Car;
import com.busstation.entities.TypeCar;
import com.busstation.payload.request.TypeCarRequest;
import com.busstation.payload.response.CarResponse;

import java.util.List;
import java.util.Optional;


public interface TypeCarService {
    List<TypeCar> getAllTypeCar();

    List<CarResponse> getCarsByTypeCar(int typeCar);

    TypeCar addTypeCar(TypeCarRequest typeCarRequest);

    TypeCar upDateTypeCar(int typeCarId,TypeCarRequest typeCarRequest);

    String deleteTypeCar(int typeCarId);
}
