package com.example.rental.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.rental.entity.Car;

/**
 * Tests for RentalService Business Logic
 * Tests the service layer without mocking (unit tests with real objects)
 */
@DisplayName("RentalService Unit Tests")
class RentalServiceTest {

    private RentalService rentalService;

    @BeforeEach
    void setUp() {
        rentalService = new RentalService();
    }

    // ============ getAllCars() ============

    @Test
    @DisplayName("Should return all cars from the service")
    void testGetAllCars() {
        var allCars = rentalService.getAllCars();

        assertNotNull(allCars);
        assertEquals(3, allCars.size());
    }

    // ============ getCarByPlate() ============

    @Test
    @DisplayName("Should find a car by plate number")
    void testGetCarByPlate_Found() {
        Optional<Car> car = rentalService.getCarByPlate("ABC123");

        assertTrue(car.isPresent());
        assertEquals("ABC123", car.get().getPlate());
        assertEquals("Toyota", car.get().getBrand());
        assertFalse(car.get().isRented());
    }

    @Test
    @DisplayName("Should return empty when car not found")
    void testGetCarByPlate_NotFound() {
        Optional<Car> car = rentalService.getCarByPlate("NONEXISTENT");

        assertFalse(car.isPresent());
    }

    // ============ rentCar() ============

    @Test
    @DisplayName("Should successfully rent a car that is not rented")
    void testRentCar_Success() {
        boolean result = rentalService.rentCar("ABC123");

        assertTrue(result);
        Optional<Car> car = rentalService.getCarByPlate("ABC123");
        assertTrue(car.isPresent());
        assertTrue(car.get().isRented());
    }

    @Test
    @DisplayName("Should fail to rent an already rented car")
    void testRentCar_AlreadyRented() {
        // First rental
        rentalService.rentCar("ABC123");

        // Try to rent again
        boolean result = rentalService.rentCar("ABC123");

        assertFalse(result);
    }

    @Test
    @DisplayName("Should fail to rent a non-existent car")
    void testRentCar_NotFound() {
        boolean result = rentalService.rentCar("NONEXISTENT");

        assertFalse(result);
    }

    // ============ returnCar() ============

    @Test
    @DisplayName("Should successfully return a rented car")
    void testReturnCar_Success() {
        // Rent a car first
        rentalService.rentCar("ABC123");

        // Return the car
        boolean result = rentalService.returnCar("ABC123");

        assertTrue(result);
        Optional<Car> car = rentalService.getCarByPlate("ABC123");
        assertTrue(car.isPresent());
        assertFalse(car.get().isRented());
    }

    @Test
    @DisplayName("Should fail to return a car that is not rented")
    void testReturnCar_NotRented() {
        boolean result = rentalService.returnCar("ABC123");

        assertFalse(result);
    }

    @Test
    @DisplayName("Should fail to return a non-existent car")
    void testReturnCar_NotFound() {
        boolean result = rentalService.returnCar("NONEXISTENT");

        assertFalse(result);
    }

    // ============ addCar() ============

    @Test
    @DisplayName("Should add a new car to the service")
    void testAddCar() {
        Car newCar = new Car("NEW999", "Tesla");
        rentalService.addCar(newCar);

        Optional<Car> retrievedCar = rentalService.getCarByPlate("NEW999");
        assertTrue(retrievedCar.isPresent());
        assertEquals("NEW999", retrievedCar.get().getPlate());
        assertEquals("Tesla", retrievedCar.get().getBrand());
    }

    // ============ Rental Workflow Scenarios ============

    @Test
    @DisplayName("Complete rental workflow: rent and return")
    void testCompleteRentalWorkflow() {
        String plateNumber = "XYZ789";

        // Check initial state
        Optional<Car> carBefore = rentalService.getCarByPlate(plateNumber);
        assertTrue(carBefore.isPresent());
        assertFalse(carBefore.get().isRented());

        // Rent the car
        boolean rentResult = rentalService.rentCar(plateNumber);
        assertTrue(rentResult);

        // Check rented state
        Optional<Car> carRented = rentalService.getCarByPlate(plateNumber);
        assertTrue(carRented.isPresent());
        assertTrue(carRented.get().isRented());

        // Return the car
        boolean returnResult = rentalService.returnCar(plateNumber);
        assertTrue(returnResult);

        // Check returned state
        Optional<Car> carAfter = rentalService.getCarByPlate(plateNumber);
        assertTrue(carAfter.isPresent());
        assertFalse(carAfter.get().isRented());
    }

    @Test
    @DisplayName("Should handle multiple car rentals independently")
    void testMultipleCarRentals() {
        // Rent first car
        assertTrue(rentalService.rentCar("ABC123"));

        // Rent second car
        assertTrue(rentalService.rentCar("XYZ789"));

        // Verify both are rented
        assertTrue(rentalService.getCarByPlate("ABC123").get().isRented());
        assertTrue(rentalService.getCarByPlate("XYZ789").get().isRented());

        // Return first car
        assertTrue(rentalService.returnCar("ABC123"));

        // Verify states
        assertFalse(rentalService.getCarByPlate("ABC123").get().isRented());
        assertTrue(rentalService.getCarByPlate("XYZ789").get().isRented());
    }
}
