package com.CarFuelManagement.CarFuelManagement.model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private Long id;
    private String model;
    private String brand;
    private int year;
    private List<Fuel> fuels = new ArrayList<>();

    public Car(Long id, String model, String brand, int year) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.fuels = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Fuel> getFuels() {
        return fuels;

    }

    public void addFuel(Fuel fuel) {
        this.fuels.add(fuel);
    }

    public void setFuels(List<Fuel> fuels) {
        this.fuels = fuels;
    }

}
