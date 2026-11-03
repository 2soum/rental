package com.example.rental.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for Car Entity
 * Tests the data model and its behavior
 */
@DisplayName("Car Entity Tests")
class CarTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car("ABC123", "Toyota");
    }

    // ============ Constructor ============

    @Test
    @DisplayName("Should create a car with correct attributes")
    void testCarConstructor() {
        assertEquals("ABC123", car.getPlate());
        assertEquals("Toyota", car.getBrand());
        assertFalse(car.isRented());
    }

    @Test
    @DisplayName("Should create a car with default constructor")
    void testCarDefaultConstructor() {
        Car defaultCar = new Car();

        assertNull(defaultCar.getPlate());
        assertNull(defaultCar.getBrand());
        assertFalse(defaultCar.isRented());
    }

    // ============ Getters and Setters ============

    @Test
    @DisplayName("Should get and set plate number")
    void testPlateGetterSetter() {
        car.setPlate("XYZ789");

        assertEquals("XYZ789", car.getPlate());
    }

    @Test
    @DisplayName("Should get and set brand")
    void testBrandGetterSetter() {
        car.setBrand("Honda");

        assertEquals("Honda", car.getBrand());
    }

    @Test
    @DisplayName("Should get and set rented status")
    void testRentedGetterSetter() {
        assertFalse(car.isRented());

        car.setRented(true);
        assertTrue(car.isRented());

        car.setRented(false);
        assertFalse(car.isRented());
    }

    // ============ State Management ============

    @Test
    @DisplayName("Car should start in not-rented state")
    void testInitialState() {
        Car newCar = new Car("DEF456", "BMW");

        assertFalse(newCar.isRented());
    }

    @Test
    @DisplayName("Should toggle rented state multiple times")
    void testToggleRentedState() {
        // Initial state: not rented
        assertFalse(car.isRented());

        // Rent
        car.setRented(true);
        assertTrue(car.isRented());

        // Return
        car.setRented(false);
        assertFalse(car.isRented());

        // Rent again
        car.setRented(true);
        assertTrue(car.isRented());
    }

    // ============ Data Integrity ============

    @Test
    @DisplayName("Rented status should not affect plate and brand")
    void testDataIntegrityAfterRental() {
        String originalPlate = car.getPlate();
        String originalBrand = car.getBrand();

        car.setRented(true);

        assertEquals(originalPlate, car.getPlate());
        assertEquals(originalBrand, car.getBrand());

        car.setRented(false);

        assertEquals(originalPlate, car.getPlate());
        assertEquals(originalBrand, car.getBrand());
    }

    @Test
    @DisplayName("Should create independent car instances")
    void testCarIndependence() {
        Car car1 = new Car("ABC123", "Toyota");
        Car car2 = new Car("XYZ789", "Honda");

        car1.setRented(true);

        assertTrue(car1.isRented());
        assertFalse(car2.isRented());
    }
}
