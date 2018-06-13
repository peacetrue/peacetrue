package com.github.peacetrue.festivalschedule;

import java.time.Year;
import java.util.List;

/**
 * to register {@link FestivalSchedule}
 *
 * @author xiayx
 */
public interface FestivalScheduleRegistrar {

    /**
     * set festival schedule list for special year
     *
     * @param year              the special year
     * @param festivalSchedules the festival schedule list
     */
    void setFestivalSchedules(Year year, List<FestivalSchedule> festivalSchedules);

}
