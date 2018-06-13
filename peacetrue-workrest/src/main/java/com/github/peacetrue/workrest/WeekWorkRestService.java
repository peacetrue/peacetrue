package com.github.peacetrue.workrest;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Judging workday and restday based on weekday and weekend
 *
 * @author xiayx
 */
public class WeekWorkRestService implements WorkRestService {

    @Override
    public boolean isWorkday(LocalDate localDate) {
        return WorkRestUtils.isWeekday(localDate);
    }

    @Override
    public boolean isRestday(LocalDate localDate) {
        return WorkRestUtils.isWeekend(localDate);
    }

    @Override
    public List<MonthDay> getWorkdays(Year year) {
        LocalDate firstDayOfYear = LocalDate.ofYearDay(year.getValue(), 1);
        Temporal lastDayOfYear = TemporalAdjusters.lastDayOfYear().adjustInto(firstDayOfYear);
        return Stream.iterate(firstDayOfYear, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(firstDayOfYear, lastDayOfYear) + 1)
                .filter(WorkRestUtils::isWeekday).map(MonthDay::from).collect(Collectors.toList());
    }

    @Override
    public List<MonthDay> getRestdays(Year year) {
        LocalDate firstDayOfYear = LocalDate.ofYearDay(year.getValue(), 1);
        Temporal lastDayOfYear = TemporalAdjusters.lastDayOfYear().adjustInto(firstDayOfYear);
        return Stream.iterate(firstDayOfYear, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(firstDayOfYear, lastDayOfYear) + 1)
                .filter(WorkRestUtils::isWeekend).map(MonthDay::from).collect(Collectors.toList());
    }

}
