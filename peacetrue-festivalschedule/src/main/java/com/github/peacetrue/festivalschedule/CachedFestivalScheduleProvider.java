package com.github.peacetrue.festivalschedule;

import java.time.Year;
import java.util.*;

/**
 * a cached {@link FestivalScheduleProvider}
 *
 * @author xiayx
 */
public class CachedFestivalScheduleProvider implements FestivalScheduleProvider, FestivalScheduleRegistrar {

    private Map<Year, List<FestivalSchedule>> yearFestivalSchedules = new HashMap<>(16);

    @Override
    public List<FestivalSchedule> getFestivalSchedules(Year year) {
        return Optional.ofNullable(yearFestivalSchedules.get(year)).orElse(Collections.emptyList());
    }

    @Override
    public void setFestivalSchedules(Year year, List<FestivalSchedule> festivalSchedules) {
        yearFestivalSchedules.put(year, festivalSchedules);
    }
}
