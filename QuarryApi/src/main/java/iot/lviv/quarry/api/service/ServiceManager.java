package iot.lviv.quarry.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceManager {

    private static CarService carService;
    private static DriverService driverService;
    private static QuarryService quarryService;

    @Autowired
    private CarService autowiredCarService;

    @Autowired
    private DriverService autowiredDriverService;

    @Autowired
    private QuarryService autowiredQuarryService;


    @PostConstruct
    private void init() {
        driverService = autowiredDriverService;
        carService = autowiredCarService;
        quarryService = autowiredQuarryService;

        carService.setDriverService(driverService);
        quarryService.setCarService(carService);
    }

    public static CarService getCarService() {
        return carService;
    }

    public static DriverService getDriverService() {
        return driverService;
    }

    public static QuarryService getQuarryService() {
        return quarryService;
    }
}
