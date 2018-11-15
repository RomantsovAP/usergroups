package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateTimeUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeUtil() {
    }

    public static String toString(LocalDate date) {
        return date == null ? "" : date.format(DATE_FORMATTER);
    }

    public static LocalDate parseLocalDate(String str) {
        return str.isEmpty() ? null : LocalDate.parse(str);
    }
}
