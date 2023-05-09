package com.busstation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tbl_type_car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_carId")
    private int typeCarId;

    @Column(name = "type_car_name", nullable = false,unique = true)
    private String typeCarName;

    @Column(name = "total_charis")
    private int totalChairs;

    @OneToMany(mappedBy = "typeCar")
    @JsonIgnore
    private Set<Car> listTypeCar;

}
