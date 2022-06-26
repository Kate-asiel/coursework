package iot.lviv.quarry.api.filestorage;

import iot.lviv.quarry.api.model.Quarry;
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
public class QuarryFileStore {
    private static final String QUARRY_PATH = "src/main/resources/files/quarry.";
    private static final String ZERO_BEFORE_DATE = "0";
    private static final String FILE_FORMAT = ".csv";
    private Map<Long, Quarry> quarries = new HashMap();
    private Long index = 0L;

    @PreDestroy
    private void saveQuarries() throws IOException {
        List<Quarry> list = quarries.values().stream().toList();
        saveQuarriesToCSV(list);
    }

    @PostConstruct
    private void loadQuarriesToHashMap() throws IOException {
        if (loadQuarry() != null) {
            List<Quarry> list = loadQuarry();
            for (Quarry quarry1 : list) {
                this.quarries.put(quarry1.getQuarryId(), quarry1);

                if (quarry1.getQuarryId() > index){
                    index = quarry1.getQuarryId();
                }
            }
        }
    }

    public List<Quarry> loadQuarry() throws IOException {
        List<Quarry> list = new LinkedList<>();
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
                String pathBeforeTenDays = QUARRY_PATH + year + "." + month + "." + ZERO_BEFORE_DATE + dayIterator + FILE_FORMAT;
                if (Files.exists(Paths.get(pathBeforeTenDays))) {
                    file = new File(pathBeforeTenDays);
                    list.addAll(readQuarry(file));
                }
            } else {
                String pathAfterTenDays = QUARRY_PATH + year + "." + month + "." + dayIterator + FILE_FORMAT;
                if (Files.exists(Paths.get(pathAfterTenDays))) {
                    file = new File(pathAfterTenDays);
                    list.addAll(readQuarry(file));
                }
            }

        }
        return list;
    }

    private List<Quarry> readQuarry(File file) throws IOException {
        List<Quarry> result = new LinkedList<>();
        // skip headers
        boolean skipLine = true;
        Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);
        while (scanner.hasNextLine()) {
            if (!skipLine) {
                List<String> values = Arrays.stream(scanner.nextLine().split(", ")).toList();
                Quarry quarry = new Quarry();
                int index = 0;
                for (String value : values) {
                    switch (index) {
                        case 0: {
                            quarry.setQuarryId(Long.parseLong(value));
                            break;
                        }
                        case 1: {
                            quarry.setName(value);
                            break;
                        }
                        case 2: {
                            quarry.setLocation(value);
                            break;
                        }
                    }
                    index++;
                }
                result.add(quarry);
            } else {
                scanner.nextLine();
                skipLine = false;
            }
        }
        return result;
    }


    public void saveQuarriesToCSV(List<Quarry> quarries) throws IOException {
        String date = Helper.getCurrentTime();

        File file = new File(QUARRY_PATH + date + FILE_FORMAT);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(Quarry.getHeaders() + "\n");
            for (Quarry quarry : quarries) {
                writer.write(quarry.toCSV() + "\n");
            }
        }
    }

    public Map<Long, Quarry> getQuarries() {
        return quarries;
    }

    public Long getIndex() {
        return index;
    }

    public Long incrementIndex() {
        index++;
        return index;
    }
}

