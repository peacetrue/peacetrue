package com.github.peacetrue.festivalschedule;

import java.time.MonthDay;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;

/**
 * festival schedule
 *
 * @author xiayx
 */
public class FestivalSchedule {

    public static final String PROPERTY_DATE = "date";
    public static final String PROPERTY_RESTDAYS = "restdays";
    public static final String PROPERTY_WORKDAYS = "workdays";

    private Festival festival;
    private MonthDay date;// must be MonthDay
    private TemporalAccessor[] restdays;// MonthDay or LocalDate, NEW_YEAR may cross year
    private TemporalAccessor[] workdays;// MonthDay or LocalDate, NEW_YEAR may cross year

    @Override
    public String toString() {
        return String.format("festival: %s, date: %s, restdays: %s, workdays: %s",
                festival, date, Arrays.toString(restdays), Arrays.toString(workdays));
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public MonthDay getDate() {
        return date;
    }

    public void setDate(MonthDay date) {
        this.date = date;
    }

    public TemporalAccessor[] getRestdays() {
        return restdays;
    }

    public void setRestdays(TemporalAccessor[] restdays) {
        this.restdays = restdays;
    }

    public TemporalAccessor[] getWorkdays() {
        return workdays;
    }

    public void setWorkdays(TemporalAccessor[] workdays) {
        this.workdays = workdays;
    }
}
