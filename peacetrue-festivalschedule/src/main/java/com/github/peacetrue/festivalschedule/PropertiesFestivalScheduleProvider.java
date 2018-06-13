package com.github.peacetrue.festivalschedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Year;
import java.util.*;

/**
 * load FestivalSchedule from Properties
 *
 * @author xiayx
 */
public class PropertiesFestivalScheduleProvider implements FestivalScheduleProvider {

    public static final String NAME = "festival-schedule.properties";

    private Logger logger = LoggerFactory.getLogger(getClass());
    private String name = "/festival-schedule.properties";
    private FestivalScheduleRegistrar festivalScheduleRegistrar;

    @Override
    public List<FestivalSchedule> getFestivalSchedules(Year year) {
        logger.debug("get festival schedules of {} from properties", year);
        //load Properties
        Properties localProperties = loadProperties(NAME);
        Properties extendedProperties = loadProperties(name);
        localProperties.putAll(extendedProperties);
        // resolve Properties
        Map<Year, List<FestivalSchedule>> yearFestivalSchedules = FestivalScheduleUtils.resolveProperties(localProperties);
        logger.debug("got yearFestivalSchedules: {}", yearFestivalSchedules);
        register(yearFestivalSchedules);
        // get FestivalSchedule
        return Optional.ofNullable(yearFestivalSchedules.get(year)).orElse(Collections.emptyList());
    }

    private void register(Map<Year, List<FestivalSchedule>> festivalSchedules) {
        if (festivalScheduleRegistrar == null) return;
        for (Map.Entry<Year, List<FestivalSchedule>> entry : festivalSchedules.entrySet()) {
            logger.debug("register festivalSchedules: {} for year: {}", entry.getValue(), entry.getKey());
            festivalScheduleRegistrar.setFestivalSchedules(entry.getKey(), entry.getValue());
        }
    }

    private Properties loadProperties(String name) {
        Properties properties = new Properties();
        InputStream resourceAsStream = getClass().getResourceAsStream(name);
        if (resourceAsStream == null) return properties;
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            logger.warn("load Properties from {}", name, e);
        }
        logger.debug("load Properties {} from {}", properties, name);
        return properties;
    }


    public void setFestivalScheduleRegistrar(FestivalScheduleRegistrar festivalScheduleRegistrar) {
        this.festivalScheduleRegistrar = festivalScheduleRegistrar;
    }

    public void setName(String name) {
        this.name = name;
    }


}
