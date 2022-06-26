package iot.lviv.quarry.api.filestorage;

import iot.lviv.quarry.api.model.Car;
import iot.lviv.quarry.api.model.enums.CarTypeEnum;
import iot.lviv.quarry.helper.Helper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class CarFileStore {
    private static final String CAR_PATH = "src/main/resources/files/car.";
    private static final String ZERO_BEFORE_DATE = "0";
    private static final String FILE_FORMAT = ".csv";
    private Map<Long, Car> cars = new HashMap<>();
    private Long countId = 0L;


    @PreDestroy
    private void saveCars() throws IOException {
        List<Car> list = cars.values().stream().toList();
        saveCarsToCsv(list);
    }

    @PostConstruct
    private void loadCarsToHashMap() throws IOException {
        List<Car> list = loadCars();

        if (list != null) {
            for (Car car : list) {
                cars.put(car.getCarId(), car);

                if (car.getCarId() > countId) {
                    countId = car.getCarId();
                }
            }
        }
    }

    public List<Car> loadCars() throws IOException {
        List<Car> list = new LinkedList<>();
        String year = Integer.toString(LocalDate.now().getYear());
        int monthValue = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();

        File file;
        String month;

        if (monthValue < 10) {
            month = ZERO_BEFORE_DATE + monthValue;
        } else {
            month = Integer.toString(monthValue);
        }

        for (int dayIterator = 1; dayIterator <= day; dayIterator++) {
            if (dayIterator < 10) {
                String pathBeforeTenDays = CAR_PATH + year + "." + month + "."
                        + ZERO_BEFORE_DATE + dayIterator + FILE_FORMAT;
                if (Files.exists(Paths.get(pathBeforeTenDays))) {
                    file = new File(pathBeforeTenDays);
                    list.addAll(readCars(file));
                }
            } else {
                String pathAfterTenDays = CAR_PATH + year + "." + month + "." + dayIterator + FILE_FORMAT;
                if (Files.exists(Paths.get(pathAfterTenDays))) {
                    file = new File(pathAfterTenDays);
                    list.addAll(readCars(file));
                }
            }

        }
        return list;
    }

    private List<Car> readCars(File file) throws IOException {
        List<Car> result = new LinkedList<>();
        // skip headers
        boolean skipLine = true;
        Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);
        while (scanner.hasNextLine()) {
            if (!skipLine) {
                List<String> values = Arrays.stream(scanner.nextLine().split(", ")).toList();
                Car car = new Car();
                int index = 0;
                for (String value : values) {
                    switch (index) {
                        case 0: {
                            car.setCarId(Long.parseLong(value));
                            break;
                        }
                        case 1: {
                            car.setNumber(String.valueOf(value));
                            break;
                        }
                        case 2: {
                            car.setType(CarTypeEnum.valueOf(value));
                            break;
                        }
                        case 3: {
                            if (value.equals("null")) {
                                car.setQuarryId(null);
                            } else {
                                car.setQuarryId(Long.parseLong(value));
                            }
                            break;
                        }
                    }
                    index++;
                }
                result.add(car);
            } else {
                scanner.nextLine();
                skipLine = false;
            }
        }
        return result;
    }

    public void saveCarsToCsv(List<Car> cars) throws IOException {
        String date = Helper.getCurrentTime();
        File file = new File(CAR_PATH + date + FILE_FORMAT);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(Car.getHeaders() + "\n");
            for (Car car : cars) {
                writer.write(car.toCSV() + "\n");
            }
        }
    }

    public Map<Long, Car> getCars() {
        return cars;
    }

    public Long getCountId() {
        return countId;
    }

    public Long incrementIndex() {
        countId++;
        return countId;
    }

}
