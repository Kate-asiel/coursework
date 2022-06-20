package iot.lviv.quarry.api.service;

import iot.lviv.quarry.api.filestorage.CarFileStore;
import iot.lviv.quarry.api.model.Car;
import iot.lviv.quarry.api.model.Driver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Service
public class CarService {

    @Autowired
    private CarFileStore carFileStore;


    private DriverService driverService;

    public List<Car> getAllCars() {
        return new ArrayList<>(carFileStore.getCars().values());
    }

    public List<Car> getCarsByQuarryId(Long quarryId) {
        List<Car> list = getAllCars();
        return list.stream().filter(car -> Objects.equals(car.getQuarryId(), quarryId)).toList();
    }

    public Car getCarById(Long carId) {
        return carFileStore.getCars().get(carId);
    }

    public Car addCar(Car car) {
        long index = carFileStore.incrementIndex();
        car.setCarId(index);
        carFileStore.getCars().put(index, car);
        return car;
    }

    public Car changeCar(Long carId, Car car) {
        car.setCarId(carId);
        carFileStore.getCars().put(carId, car);
        return car;
    }

    public Car deleteCar(Long carId) {
        Car car = carFileStore.getCars().remove(carId);
        List<Driver> drivers = driverService.getDriversByCarId(carId);
        for (Driver driver : drivers) {
            driver.setCarId(null);
            driverService.changeDriver(driver.getDriverId(), driver);
        }
        return car;
    }

    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }
}


