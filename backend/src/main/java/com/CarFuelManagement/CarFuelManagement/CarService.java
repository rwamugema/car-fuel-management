package com.CarFuelManagement.CarFuelManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.CarFuelManagement.CarFuelManagement.model.Car;
import com.CarFuelManagement.CarFuelManagement.model.Fuel;


@Service
public class CarService {

    private Long nextId = 1L;

    private final Map<Long, Car> cars = new HashMap<>();

    public Car createCar(String model, String brand, int year) {
        Car car = new Car(nextId++, model, brand, year);
        cars.put(car.getId(), car);
        return car;
    }

    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }

    public void addCar(Car car) {
        cars.put(car.getId(), car);
    }

    public Fuel addFuelToCar(Long id, Integer liters, Integer price, Integer odometers) {
        Car car = cars.get(id);
        if (car == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }
        Fuel fuel = new Fuel(liters, price, odometers);
        car.getFuels().add(fuel);
        return fuel;
    }
    
    public Map<String, Object> getFuelStats(Long id) {
        Car car = cars.get(id);
        if (car == null) {
            throw new IllegalArgumentException("Car not found");
        }

        List<Fuel> entries = car.getFuels();
        if (entries.isEmpty()) {
            Map<String, Object> emptyResult = new HashMap<>();
            emptyResult.put("totalFuel", 0.0);
            emptyResult.put("totalCost", 0.0);
            emptyResult.put("avgConsumption", 0.0);
            return emptyResult;
        }

        double totalFuel = entries.stream()
                .mapToDouble(Fuel::getLiters).sum();
        double totalCost = entries.stream()
                .mapToDouble(Fuel::getPrice).sum();

        double avgConsumption = 0.0;
        if (entries.size() >= 2) {
            int firstOdo = entries.get(0).getOdometers();
            int lastOdo = entries.get(entries.size() - 1).getOdometers();
            int distance = lastOdo - firstOdo;

            if (distance > 0) {
                avgConsumption = (totalFuel / distance) * 100;
            } 
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalFuel", totalFuel);
        result.put("totalCost", totalCost);
        result.put("avgConsumption", avgConsumption);
        return result;
    }

}
