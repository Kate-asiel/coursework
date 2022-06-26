package iot.lviv.quarry.api.service;

import iot.lviv.quarry.api.filestorage.QuarryFileStore;
import iot.lviv.quarry.api.model.Car;
import iot.lviv.quarry.api.model.Quarry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
public class QuarryService {

    CarService carService;

    @Autowired
    private QuarryFileStore quarryFileStore;

    public List<Quarry> getAllQuarries() {
        return new ArrayList<>(quarryFileStore.getQuarries().values());
    }

    public Quarry getQuarryById(Long quarryId) {
        return quarryFileStore.getQuarries().get(quarryId);
    }

    public Quarry addQuarry(Quarry quarry) {
        long index = quarryFileStore.incrementIndex();
        quarry.setQuarryId(index);
        quarryFileStore.getQuarries().put(index, quarry);
        return quarry;
    }


    public Quarry changeQuarry(Long quarryId, Quarry quarry1) {
        quarry1.setQuarryId(quarryId);
        quarryFileStore.getQuarries().put(quarryId, quarry1);
        return quarry1;
    }

    public Quarry deleteQuarry(Long quarryId) {
        Quarry quarry = quarryFileStore.getQuarries().remove(quarryId);

        List<Car> cars = carService.getCarsByQuarryId(quarryId);

        for (Car car : cars) {
            car.setQuarryId(null);
            carService.changeCar(car.getCarId(), car);
        }
        return quarry;
    }

    public void setCarService(CarService carService) {
        this.carService = carService;
    }
}