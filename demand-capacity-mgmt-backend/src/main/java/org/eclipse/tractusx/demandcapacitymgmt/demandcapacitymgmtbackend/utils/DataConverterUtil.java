package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;
import lombok.experimental.UtilityClass;

public class DataConverterUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDateTime convertFromString(String date) {
        LocalDate ld = LocalDate.parse(date, formatter);
        return LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
    }

    public static Boolean itsMonday(String date) {
        LocalDateTime localDateTime = convertFromString(date);
        String dayOfWeek = localDateTime.getDayOfWeek().toString();
        return "MONDAY".equals(dayOfWeek);
    }

    public static Boolean checkListAllMonday(List<LocalDateTime> dates) {
        return dates.stream().allMatch(dateTime -> dateTime.getDayOfWeek() == DayOfWeek.MONDAY);
    }

    public static Boolean checkDatesSequence(List<LocalDateTime> dates) {
        boolean isSequentialWeeks = true;
        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDateTime currentDateTime = dates.get(i);
            LocalDateTime nextDateTime = dates.get(i + 1);
            if (
                nextDateTime.getLong(ChronoField.ALIGNED_WEEK_OF_YEAR) !=
                currentDateTime.getLong(ChronoField.ALIGNED_WEEK_OF_YEAR) +
                1
            ) {
                isSequentialWeeks = false;
                break;
            }
        }
        return isSequentialWeeks;
    }
}
