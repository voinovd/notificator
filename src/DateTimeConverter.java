import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

public class DateTimeConverter {

    private static final String DD_MM_YYYY = "dd.MM.yyyy";

    public DateTimePeriod convertStringToDateTimePeriod(String stringPeriod) {
        DateTimePeriod period = new DateTimePeriod();

        LocalDateTime start = null;
        LocalDateTime end = null;

        if ( "today".equals(stringPeriod) ) {
            LocalDate currentDate = LocalDate.now();
            start = currentDate.atStartOfDay();
            end = start.plusDays(1).minusSeconds(1);
            return new DateTimePeriod(start, end);
        }

        if ( "tomorrow".equals(stringPeriod) ) {
            LocalDate currentDate = LocalDate.now();
            start = currentDate.atStartOfDay().plusDays(1);
            end = start.plusDays(1).minusSeconds(1);
            return new DateTimePeriod(start, end);
        }

        if ( "current week".equals(stringPeriod) ) {
            LocalDate currentDate = LocalDate.now();
            int dayOfWeekNumber = currentDate.getDayOfWeek().getValue();
            start = currentDate.atStartOfDay().minusDays(dayOfWeekNumber - 1);
            end = start.plusDays(7).minusSeconds(1);
            return new DateTimePeriod(start, end);
        }

        if ( "next week".equals(stringPeriod) ) {
            LocalDate currentDateInNextWeek = LocalDate.now().plusDays(7);
            int dayOfWeekNumber = currentDateInNextWeek.getDayOfWeek().getValue();
            start = currentDateInNextWeek.atStartOfDay().minusDays(dayOfWeekNumber - 1);
            end = start.plusDays(7).minusSeconds(1);
            return new DateTimePeriod(start, end);
        }

        if ( "current month".equals(stringPeriod) ) {
            LocalDate currentDate = LocalDate.now();
            LocalDate firstDayOfCurrentMonth = currentDate.withDayOfMonth(1);
            start = firstDayOfCurrentMonth.atStartOfDay();
            end = start.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).minusSeconds(1);
            return new DateTimePeriod(start, end);
        }

        if ( "next month".equals(stringPeriod) ) {
            LocalDate currentDate = LocalDate.now();
            LocalDate firstDayOfCurrentMonth = currentDate.withDayOfMonth(1);
            LocalDate firstDayOfNextMonth = firstDayOfCurrentMonth.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
            start = firstDayOfNextMonth.atStartOfDay();
            end = start.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).minusSeconds(1);
            return new DateTimePeriod(start, end);
        }

        if ( "current year".equals(stringPeriod) ) {
            LocalDate currentDate = LocalDate.now();
            LocalDate firstDayOfCurrentYear = currentDate.withDayOfYear(1);
            start = firstDayOfCurrentYear.atStartOfDay();
            end = start.with(TemporalAdjusters.lastDayOfYear()).plusDays(1).minusSeconds(1);
            return new DateTimePeriod(start, end);
        }

        if ( stringPeriod.contains("-")) {
            String parts[] = stringPeriod.split("-");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MM_YYYY);
            try {
                start = LocalDateTime.parse(parts[0], formatter);
                end = LocalDateTime.parse(parts[1], formatter);
                return new DateTimePeriod(start, end);
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MM_YYYY);
        try {
            LocalDate date = LocalDate.parse(stringPeriod, formatter);
            start = date.atStartOfDay();
            end = start.plusDays(1).minusSeconds(1);
            return new DateTimePeriod(start, end);
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
