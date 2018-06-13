package com.github.peacetrue.festivalschedule;

import org.junit.Assert;
import org.junit.Test;

import java.time.Year;
import java.util.List;

/**
 * tests for {@link PropertiesFestivalScheduleProvider}
 *
 * @author xiayx
 */
public class PropertiesFestivalScheduleProviderTest {

    private PropertiesFestivalScheduleProvider provider = new PropertiesFestivalScheduleProvider();
    private CachedFestivalScheduleProvider cachedProvider = new CachedFestivalScheduleProvider();

    public PropertiesFestivalScheduleProviderTest() {
        provider.setFestivalScheduleRegistrar(cachedProvider);
    }

    @Test
    public void getFestivalSchedules() throws Exception {
        List<FestivalSchedule> festivalSchedules = provider.getFestivalSchedules(Year.of(2018));
        Assert.assertEquals(7, festivalSchedules.size());
    }

}