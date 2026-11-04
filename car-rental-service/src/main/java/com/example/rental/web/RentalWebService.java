
package com.example.rental.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rental.entity.Car;
import com.example.rental.service.RentalService;

/**
 * RentalWebService - REST API for car rental management
 */
@RestController
@RequestMapping("/cars")
public class RentalWebService {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(rentalService.getAllCars());
    }

    @GetMapping("/{plateNumber}")
    public ResponseEntity<Car> getCarByPlate(@PathVariable String plateNumber) {
        Optional<Car> car = rentalService.getCarByPlate(plateNumber);
        if (car.isPresent()) {
            return ResponseEntity.ok(car.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{plateNumber}")
    public ResponseEntity<String> updateCarRentalStatus(
            @PathVariable String plateNumber,
            @RequestParam(name = "rent", required = false) Boolean rent,
            @RequestBody Dates dates) {
        
        Optional<Car> car = rentalService.getCarByPlate(plateNumber);
        if (!car.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (rent != null && rent) {
            if (rentalService.rentCar(plateNumber)) {
                return ResponseEntity.ok("Car " + plateNumber + " rented successfully");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Car " + plateNumber + " is already rented");
            }
        } else if (rent != null && !rent) {
            if (rentalService.returnCar(plateNumber)) {
                return ResponseEntity.ok("Car " + plateNumber + " returned successfully");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Car " + plateNumber + " is not rented");
            }
        }

        return ResponseEntity.badRequest().body("Invalid request parameter");
    }
}