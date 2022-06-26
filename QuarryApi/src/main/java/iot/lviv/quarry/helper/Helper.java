package iot.lviv.quarry.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper {
    public static String getCurrentTime() {
        Date currentTime = Calendar.getInstance().getTime();
        return new SimpleDateFormat("yyyy.MM.dd").format(currentTime);
    }
}
