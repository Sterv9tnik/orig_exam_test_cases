package helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static final String formatDate(LocalDate localDate){
        return localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
