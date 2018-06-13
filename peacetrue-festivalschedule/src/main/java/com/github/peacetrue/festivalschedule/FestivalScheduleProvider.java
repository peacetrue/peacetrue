package com.github.peacetrue.festivalschedule;

import java.time.Year;
import java.util.List;

/**
 * to provide {@link FestivalSchedule}
 *
 * @author xiayx
 */
public interface FestivalScheduleProvider {

    /**
     * get festival schedule list for special year
     *
     * @param year the special year
     * @return festival schedule list
     * @throws FestivalUnscheduled
     */
    List<FestivalSchedule> getFestivalSchedules(Year year);

}
