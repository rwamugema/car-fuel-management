package com.CarFuelManagement.CarFuelManagement;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CarFuelManagement.CarFuelManagement.model.Car;
import com.CarFuelManagement.CarFuelManagement.model.Fuel;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }
    
    @GetMapping
    public List<Car> getCarStatus() {
        return carService.getAllCars();
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Map<String, Object> body) {
        String brand = (String) body.get("brand");
        String model = (String) body.get("model");
        int year = (int) body.get("year");

        Car car = carService.createCar(brand, model, year);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }
    
    @PostMapping("/{id}/fuel")
    public ResponseEntity<Fuel> addFuelToCar(
        @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        Integer liters = (Integer) body.get("liters");
        Integer price = (Integer) body.get("price");
        Integer odometers = (Integer) body.get("odometers");

        if (id == null || liters == null || price == null || odometers == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Fuel fuel = carService.addFuelToCar(id, liters, price, odometers);
        return ResponseEntity.status(HttpStatus.OK).body(fuel);
    }

    @GetMapping("/{id}/fuel/stats")
    public ResponseEntity<Map<String, Object>> getFuelStats(@PathVariable Long id) {
        Map<String, Object> stats = carService.getFuelStats(id);
        return ResponseEntity.status(HttpStatus.OK).body(stats);
    }
}
