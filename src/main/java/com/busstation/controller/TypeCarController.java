package com.busstation.controller;

import com.busstation.entities.Car;
import com.busstation.entities.TypeCar;
import com.busstation.payload.request.TypeCarRequest;
import com.busstation.payload.response.CarResponse;
import com.busstation.services.impl.TypeCarServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController(value = "typeCarAPIofWeb")
@RequestMapping("/api/v1/typeCar")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class TypeCarController {
    private final TypeCarServiceImpl typeCarService;

    @GetMapping("/all")
    public ResponseEntity<List<TypeCar>> getAllTypeCar() {
        return ResponseEntity.ok().body(typeCarService.getAllTypeCar());
    }

    @GetMapping("/cars/{typeCarId}")
    public ResponseEntity<List<CarResponse>>getCarsByTypeCarId(@PathVariable int typeCarId) {
        return ResponseEntity.status(HttpStatus.OK).body(typeCarService.getCarsByTypeCar(typeCarId));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TypeCar> addTypeCar(@RequestBody TypeCarRequest typeCarRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(typeCarService.addTypeCar(typeCarRequest));
    }

    @PutMapping("update/{typeCarId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TypeCar> updateTypeCar(@PathVariable int typeCarId, @RequestBody TypeCarRequest typeCarRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(typeCarService.upDateTypeCar(typeCarId,typeCarRequest));
    }

    @DeleteMapping("delete/{typeCarId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTypeCarById(@PathVariable int  typeCarId) {
        return ResponseEntity.status(HttpStatus.OK).body(typeCarService.deleteTypeCar(typeCarId));
    }
}
