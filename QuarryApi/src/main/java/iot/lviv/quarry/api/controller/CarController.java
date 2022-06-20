package iot.lviv.quarry.api.controller;

import iot.lviv.quarry.api.model.Car;
import iot.lviv.quarry.api.service.CarService;
import iot.lviv.quarry.api.service.ServiceManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;


@Lazy
@RestController
@RequestMapping("/car")
public class CarController {

    private CarService carService;

    @PostConstruct
    public void init() {
        carService = ServiceManager.getCarService();
    }

    @PostMapping
    public Car addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{carId}")
    public Car getCarById(@PathVariable Long carId) {
        return carService.getCarById(carId);
    }


    @PutMapping("/{carId}")
    public Car changeCar(@PathVariable Long carId, @RequestBody Car car) {
        return carService.changeCar(carId, car);
    }

    @DeleteMapping("/{carId}")
    public Car deleteCar(@PathVariable Long carId) {
        return carService.deleteCar(carId);
    }

    @GetMapping("/quarry/{quarryId}")
    public List<Car> getCarsByQuarryId(@PathVariable Long quarryId) {
        return carService.getCarsByQuarryId(quarryId);
    }

}
