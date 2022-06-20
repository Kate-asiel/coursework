package iot.lviv.quarry.api.controller;

import iot.lviv.quarry.api.model.Driver;
import iot.lviv.quarry.api.service.DriverService;
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
@RequestMapping("/driver")
public class DriverController {

    private DriverService driverService;

    @PostConstruct
    public void init() {
        driverService = ServiceManager.getDriverService();
    }

    @PostMapping
    public Driver addDriver(@RequestBody Driver driver) {
        return driverService.addDriver(driver);
    }

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{driverId}")
    public Driver getDriverById(@PathVariable Long driverId) {
        return driverService.getDriverById(driverId);
    }


    @PutMapping("/{driverId}")
    public Driver changeDriver(@PathVariable Long driverId, @RequestBody Driver driver) {
        return driverService.changeDriver(driverId, driver);
    }

    @DeleteMapping("/{driverId}")
    public Driver deleteDriver(@PathVariable Long driverId) {
        return driverService.deleteDriver(driverId);
    }

    @GetMapping("/car/{carId}")
    public List<Driver> getDriversByCarId(@PathVariable Long carId) {
        return driverService.getDriversByCarId(carId);
    }

}
