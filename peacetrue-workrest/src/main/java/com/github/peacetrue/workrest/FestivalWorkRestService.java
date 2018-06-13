package com.github.peacetrue.workrest;

import com.github.peacetrue.festivalschedule.FestivalSchedule;
import com.github.peacetrue.festivalschedule.FestivalScheduleProvider;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * Judging workday and restday based on festival schedule
 *
 * @author xiayx
 */
public class FestivalWorkRestService implements WorkRestService {

    private FestivalScheduleProvider festivalScheduleProvider;
    private Set<Year> years = new HashSet<>();
    private Map<Year, List<MonthDay>> workdays = new HashMap<>();
    private Map<Year, List<MonthDay>> restdays = new HashMap<>();

    private void initCache(Year year) {initCache(year, true);}

    private void initCache(Year year, boolean isCascade) {
        if (years.contains(year)) return;// already init
        years.add(year);

        List<FestivalSchedule> festivalSchedules = festivalScheduleProvider.getFestivalSchedules(year);
        festivalSchedules.forEach(festivalSchedule -> {
            addMonthDay(workdays, year, festivalSchedule.getWorkdays());
            addMonthDay(restdays, year, festivalSchedule.getRestdays());
        });
        //initialize 2019 when initializing 2018, because 2019 will affect 2018
        if (isCascade) initCache(year.plusYears(1), false);
    }

    private void addMonthDay(Map<Year, List<MonthDay>> yearMonthDays, Year year, TemporalAccessor[] temporalAccessors) {
        Arrays.stream(temporalAccessors).forEach(temporalAccessor -> {
            //2012 new year schedule 2011-12-31(SATURDAY) as workday
            Year _year = temporalAccessor instanceof LocalDate ? Year.from(temporalAccessor) : year;
            yearMonthDays.computeIfAbsent(_year, k -> new ArrayList<>()).add(MonthDay.from(temporalAccessor));
        });
    }

    @Override
    public boolean isWorkday(LocalDate localDate) {
        Year year = Year.from(localDate);
        initCache(year);
        return workdays.get(year).contains(MonthDay.from(localDate));
    }

    @Override
    public boolean isRestday(LocalDate localDate) {
        Year year = Year.from(localDate);
        initCache(year);
        return restdays.get(year).contains(MonthDay.from(localDate));
    }

    @Override
    public List<MonthDay> getWorkdays(Year year) {
        initCache(year);
        return Collections.unmodifiableList(workdays.get(year));
    }

    @Override
    public List<MonthDay> getRestdays(Year year) {
        initCache(year);
        return Collections.unmodifiableList(restdays.get(year));
    }

    public void setFestivalScheduleProvider(FestivalScheduleProvider festivalScheduleProvider) {
        this.festivalScheduleProvider = festivalScheduleProvider;
    }
}
