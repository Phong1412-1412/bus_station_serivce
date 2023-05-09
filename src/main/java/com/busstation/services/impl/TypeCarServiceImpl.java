package com.busstation.services.impl;

import com.busstation.entities.Car;
import com.busstation.entities.Trip;
import com.busstation.entities.TypeCar;
import com.busstation.exception.DataExistException;
import com.busstation.exception.DataNotFoundException;
import com.busstation.payload.request.TypeCarRequest;
import com.busstation.payload.response.CarResponse;
import com.busstation.repositories.TripRepository;
import com.busstation.repositories.TypeCarRepository;
import com.busstation.services.TypeCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeCarServiceImpl implements TypeCarService {
    private final TypeCarRepository typeCarRepository;
    private final TripRepository tripRepository;
    @Override
    public List<TypeCar> getAllTypeCar() {
        return typeCarRepository.findAll();
    }

    @Override
    public List<CarResponse> getCarsByTypeCar(int typeCar) {
        List<Car> cars = typeCarRepository.getCarsByTypeCar(typeCar);
        List<String> tripsId;
        List<CarResponse> carsResponse = new ArrayList<>();
        for(Car car : cars) {
            tripsId = new ArrayList<>();
            List<Trip> trips = tripRepository.findByCars(car);
            for(Trip trip: trips) {
                tripsId.add(trip.getTripId());
            }
            carsResponse.add(new CarResponse(car, tripsId));
        }
        return carsResponse;
    }

    @Override
    public TypeCar addTypeCar(TypeCarRequest typeCarRequest) {
        TypeCar newTypeCar = new TypeCar();
        Optional<TypeCar> typeCarExists = typeCarRepository.findByTypeCarName(typeCarRequest.getTypeCarName());
        if(typeCarExists.isPresent()) {
            throw new DataExistException("This type car is exists: ");
        }
        newTypeCar.setTypeCarName(typeCarRequest.getTypeCarName());
        newTypeCar.setTotalChairs(typeCarRequest.getTotalChairs());

        return typeCarRepository.save(newTypeCar);
    }

    @Override
    public TypeCar upDateTypeCar(int typeCarId,TypeCarRequest typeCarRequest) {
        TypeCar updateTypecar = typeCarRepository.findByTypeCarId(typeCarId).orElseThrow(() -> new DataNotFoundException("Type car with id: "+ typeCarId+ " doesn't exists"));
        updateTypecar.setTypeCarName(typeCarRequest.getTypeCarName());
        updateTypecar.setTotalChairs(typeCarRequest.getTotalChairs());
        return typeCarRepository.save(updateTypecar);
    }

    @Override
    public String deleteTypeCar(int typeCarId) {
        TypeCar deleteTypecar = typeCarRepository.findByTypeCarId(typeCarId).orElseThrow(() -> new DataNotFoundException("Type Car doesn't exists"));
        typeCarRepository.delete(deleteTypecar);
        return "Delete car with id: "+ typeCarId +" successfully";
    }
}
