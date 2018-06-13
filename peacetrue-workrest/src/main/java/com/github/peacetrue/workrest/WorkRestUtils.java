package com.github.peacetrue.workrest;

import com.github.peacetrue.festivalschedule.FestivalScheduleUtils;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Calendar;

/**
 * a utils class for {@link java.time.LocalDate} or {@link java.util.Date}
 *
 * @author xiayx
 */
public abstract class WorkRestUtils {

    /** is weekday, include 1~5 */
    public static boolean isWeekday(Temporal temporal) {
        return !isWeekend(temporal);
    }

    /** is weekend, include 6~7 */
    public static boolean isWeekend(Temporal temporal) {
        int day = temporal.get(ChronoField.DAY_OF_WEEK);
        return day == DayOfWeek.SATURDAY.getValue() || day == DayOfWeek.SUNDAY.getValue();
    }

    /** is weekday, include 1~5 */
    public static boolean isWeekday(Calendar calendar) {
        return !isWeekend(calendar);
    }

    /** is weekend, include 6~7 */
    public static boolean isWeekend(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    public static GroupWorkRestService buildGroupWorkRestService() {
        GroupWorkRestService workRestService = new GroupWorkRestService();
        FestivalWorkRestService festivalWorkRestService = new FestivalWorkRestService();
        festivalWorkRestService.setFestivalScheduleProvider(FestivalScheduleUtils.buildFestivalScheduleProvider());
        workRestService.setWorkRestServices(Arrays.asList(new WeekWorkRestService(), festivalWorkRestService));
        return workRestService;
    }
}
