package com.github.peacetrue.workrest;

import com.github.peacetrue.festivalschedule.FestivalSchedule;
import com.github.peacetrue.festivalschedule.FestivalScheduleProvider;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Judging workday and restday based on festival schedule
 *
 * @author xiayx
 */
public class FestivalWorkRestService implements WorkRestService {

    private FestivalScheduleProvider festivalScheduleProvider;

    @Override
    public boolean isWorkday(LocalDate localDate) {
        List<FestivalSchedule> festivalSchedules = festivalScheduleProvider.getFestivalSchedules(Year.from(localDate));
        MonthDay monthDay = MonthDay.from(localDate);
        return festivalSchedules.stream().map(FestivalSchedule::getWorkdays)
                .anyMatch(temporalAccessors -> Arrays.stream(temporalAccessors)
                        .anyMatch(temporalAccessor -> temporalAccessor.equals(monthDay)));
    }

    @Override
    public boolean isRestday(LocalDate localDate) {
        List<FestivalSchedule> festivalSchedules = festivalScheduleProvider.getFestivalSchedules(Year.from(localDate));
        MonthDay monthDay = MonthDay.from(localDate);
        return festivalSchedules.stream().map(FestivalSchedule::getRestdays)
                .anyMatch(temporalAccessors -> Arrays.stream(temporalAccessors)
                        .anyMatch(temporalAccessor -> temporalAccessor.equals(monthDay)));
    }

    @Override
    public List<MonthDay> getWorkdays(Year year) {
        List<FestivalSchedule> festivalSchedules = festivalScheduleProvider.getFestivalSchedules(year);
        return festivalSchedules.stream().map(FestivalSchedule::getWorkdays)
                .flatMap(Arrays::stream).map(MonthDay::from).sorted()
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthDay> getRestdays(Year year) {
        List<FestivalSchedule> festivalSchedules = festivalScheduleProvider.getFestivalSchedules(year);
        return festivalSchedules.stream().map(FestivalSchedule::getRestdays)
                .flatMap(Arrays::stream).map(MonthDay::from).sorted()
                .collect(Collectors.toList());
    }

    public void setFestivalScheduleProvider(FestivalScheduleProvider festivalScheduleProvider) {
        this.festivalScheduleProvider = festivalScheduleProvider;
    }
}
