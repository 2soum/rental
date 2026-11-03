package com.example.rental.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.rental.entity.Car;

@Service
public class RentalService {

    private List<Car> cars = new ArrayList<>();

    public RentalService() {
        // Initialize with some sample cars
        cars.add(new Car("ABC123", "Toyota"));
        cars.add(new Car("XYZ789", "Honda"));
        cars.add(new Car("DEF456", "BMW"));
    }

    public List<Car> getAllCars() {
        return new ArrayList<>(cars);
    }

    public Optional<Car> getCarByPlate(String plate) {
        return cars.stream()
                .filter(car -> car.getPlate().equals(plate))
                .findFirst();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public boolean rentCar(String plate) {
        Optional<Car> car = getCarByPlate(plate);
        if (car.isPresent() && !car.get().isRented()) {
            car.get().setRented(true);
            return true;
        }
        return false;
    }

    public boolean returnCar(String plate) {
        Optional<Car> car = getCarByPlate(plate);
        if (car.isPresent() && car.get().isRented()) {
            car.get().setRented(false);
            return true;
        }
        return false;
    }
}
