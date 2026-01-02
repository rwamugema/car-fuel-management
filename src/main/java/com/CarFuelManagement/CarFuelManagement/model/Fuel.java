package com.CarFuelManagement.CarFuelManagement.model;

public class Fuel {
    private Integer liters;
    private Integer price;
    private Integer odometers;

    public Fuel(Integer liters, Integer price, Integer odometers) {
        this.liters = liters;
        this.price = price;
        this.odometers = odometers;
    }

    public Integer getLiters() {
        return liters;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getOdometers() {
        return odometers;
    }
 
}
