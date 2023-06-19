package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

public class DataConverterUtil {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDateTime convertFromString(String date) {
        LocalDate ld = LocalDate.parse(date, formatter);
        return LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
    }

    public static Boolean itsMonday(String date) {
        LocalDateTime localDateTime = convertFromString(date);
        String dayOfWeek = localDateTime.getDayOfWeek().toString();
        return "MONDAY".equals(dayOfWeek);
    }
}
