package iot.lviv.quarry.api.filestorage;

import iot.lviv.quarry.api.model.Driver;
import iot.lviv.quarry.api.model.enums.HealthStatus;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class DriverFileStore {

    private static final String DRIVER_PATH = "src/main/resources/files/driver.";
    private static final String ZERO_BEFORE_DATE = "0";
    private static final String FILE_FORMAT = ".csv";
    private Map<Long, Driver> drivers = new HashMap<>();
    private Long index = 0L;

    @PreDestroy
    private void saveDrivers() throws IOException {
        List<Driver> list = drivers.values().stream().toList();
        saveDriversToCSV(list);
    }

    @PostConstruct
    private void loadDriversToHashMap() throws IOException {
        if (loadDrivers() != null) {
            List<Driver> list = loadDrivers();
            for (Driver driver : list) {
                drivers.put(driver.getDriverId(), driver);

                if (driver.getDriverId() > index) {
                    index = driver.getDriverId();
                }
            }
        }
    }

    public List<Driver> loadDrivers() throws IOException {
        List<Driver> list = new LinkedList<>();
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
                String pathBeforeTenDays = DRIVER_PATH + year + "." + month + "." + ZERO_BEFORE_DATE + dayIterator + FILE_FORMAT;
                if (Files.exists(Paths.get(pathBeforeTenDays))) {
                    file = new File(pathBeforeTenDays);
                    list.addAll(readDrivers(file));
                }
            } else {
                String pathAfterTenDays = DRIVER_PATH + year + "." + month + "." + dayIterator + FILE_FORMAT;
                if (Files.exists(Paths.get(pathAfterTenDays))) {
                    file = new File(pathAfterTenDays);
                    list.addAll(readDrivers(file));
                }
            }

        }
        return list;
    }

    private Collection<? extends Driver> readDrivers(File file) throws IOException {
        List<Driver> drivers = new LinkedList<>();
        // skip headers
        boolean skipLine = true;
        Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);
        while (scanner.hasNextLine()) {
            if (!skipLine) {
                List<String> values = Arrays.stream(scanner.nextLine().split(", ")).toList();
                Driver driver = new Driver();
                int index = 0;
                for (String value : values) {
                    switch (index) {
                        case 0: {
                            driver.setDriverId(Long.parseLong(value));
                            break;
                        }
                        case 1: {
                            driver.setFirstName(String.valueOf(value));
                            break;
                        }
                        case 2: {
                            driver.setLastName(String.valueOf(value));
                            break;
                        }
                        case 3: {
                            driver.setAge(Integer.parseInt(value));
                            break;
                        }
                        case 4: {
                            driver.setHealthStatus(HealthStatus.valueOf(value));
                            break;
                        }
                        case 5: {
                            if (value.equals("null")) {
                                driver.setCarId(null);
                            } else {
                                driver.setCarId(Long.parseLong(value));
                            }
                            break;
                        }
                    }
                    index++;
                }
                drivers.add(driver);
            } else {
                scanner.nextLine();
                skipLine = false;
            }
        }
        return drivers;
    }


    public void saveDriversToCSV(List<Driver> drivers) throws IOException {
        String date = Helper.getCurrentTime();

        File file = new File(DRIVER_PATH + date + FILE_FORMAT);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(Driver.getHeaders() + "\n");
            for (Driver driver : drivers) {
                writer.write(driver.toCSV() + "\n");
            }
        }
    }

    public Map<Long, Driver> getDrivers() {
        return drivers;
    }

    public Long getIndex() {
        return index;
    }

    public Long incrementIndex() {
        index++;
        return index;
    }

}
