package com.example.rental.entity;

public class Car {

    private String plate;
    private String brand;
    private boolean rented;

    public Car() {}

    public Car(String plate, String brand) {
        this.plate = plate;
        this.brand = brand;
        this.rented = false;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
}
