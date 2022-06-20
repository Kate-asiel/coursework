package iot.lviv.quarry.api.service;

import iot.lviv.quarry.api.filestorage.DriverFileStore;
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
public class DriverService {

    @Autowired
    private DriverFileStore driverFileStore;

    public List<Driver> getAllDrivers() {
        return new ArrayList<>(driverFileStore.getDrivers().values());
    }

    public Driver getDriverById(Long driverId) {
        return driverFileStore.getDrivers().get(driverId);
    }

    public Driver addDriver(Driver driver) {
        long index = driverFileStore.incrementIndex();
        driver.setDriverId(index);
        driverFileStore.getDrivers().put(index, driver);
        return driver;
    }

    public Driver changeDriver(Long driverId, Driver driver) {
        driver.setDriverId(driverId);
        driverFileStore.getDrivers().put(driverId, driver);
        return driver;
    }

    public Driver deleteDriver(Long driverId) {
        return driverFileStore.getDrivers().remove(driverId);
    }

    public List<Driver> getDriversByCarId(Long carId) {
        List<Driver> list = getAllDrivers();
        return list.stream().filter(driver -> Objects.equals(driver.getCarId(), carId)).toList();
    }

}
