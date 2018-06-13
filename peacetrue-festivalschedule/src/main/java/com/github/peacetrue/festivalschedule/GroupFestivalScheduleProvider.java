package com.github.peacetrue.festivalschedule;

import org.springframework.util.CollectionUtils;

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * delegate to a {@link FestivalScheduleProvider} collection.
 *
 * @author xiayx
 */
public class GroupFestivalScheduleProvider implements FestivalScheduleProvider {

    private List<FestivalScheduleProvider> festivalScheduleProviders;
    private FestivalScheduleRegistrar festivalScheduleRegistrar;

    @Override
    public List<FestivalSchedule> getFestivalSchedules(Year year) {
        for (FestivalScheduleProvider festivalScheduleProvider : festivalScheduleProviders) {
            List<FestivalSchedule> festivalSchedules = festivalScheduleProvider.getFestivalSchedules(year);
            if (!CollectionUtils.isEmpty(festivalSchedules)) {
                if (festivalScheduleRegistrar != null)
                    festivalScheduleRegistrar.setFestivalSchedules(year, festivalSchedules);
                return festivalSchedules;
            }
        }
        return Collections.emptyList();
    }

    public void setFestivalScheduleProviders(List<FestivalScheduleProvider> festivalScheduleProviders) {
        this.festivalScheduleProviders = Objects.requireNonNull(festivalScheduleProviders);
    }

    public void setFestivalScheduleRegistrar(FestivalScheduleRegistrar festivalScheduleRegistrar) {
        this.festivalScheduleRegistrar = festivalScheduleRegistrar;
    }
}
