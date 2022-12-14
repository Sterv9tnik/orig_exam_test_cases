package helper;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public enum BirthDates{
    TODAY_MINUS_20_YEARS_PLUS_1_DAY(LocalDate.now().minusYears(20).plusDays(1)),
    TODAY_MINUS_20_YEARS(LocalDate.now().minusYears(20)),
    TODAY_MINUS_70_YEARS_MINUS_1_DAY(LocalDate.now().minusYears(70).minusDays(1)),
    TODAY_MINUS_70_YEARS(LocalDate.now().minusYears(70));

    private String birthDate;

    BirthDates(LocalDate date) {
        this.birthDate = DateFormatter.formatDate(date);
    }
}
