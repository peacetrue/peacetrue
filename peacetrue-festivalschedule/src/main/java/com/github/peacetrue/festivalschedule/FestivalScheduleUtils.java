package com.github.peacetrue.festivalschedule;

import com.github.peacetrue.util.CollectionUtils;
import com.github.peacetrue.util.CollectorUtils;
import com.github.peacetrue.util.DateTimeFormatterUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

/**
 * a util class for festival schedule
 *
 * @author xiayx
 */
public abstract class FestivalScheduleUtils {

    /** resolve every year's FestivalSchedule from properties */
    public static Map<Year, List<FestivalSchedule>> resolveProperties(Map<?, ?> properties) {
        Map<String, Map<String, Map<String, String>>> years = group(properties);
        Map<Year, List<FestivalSchedule>> festivalSchedules = new HashMap<>();
        for (Map.Entry<String, Map<String, Map<String, String>>> entry : years.entrySet()) {
            festivalSchedules.put(
                    Year.parse(entry.getKey()),
                    entry.getValue().entrySet().stream().map(FestivalScheduleUtils::resolveFestivalSchedule).collect(Collectors.toList())
            );
        }
        return festivalSchedules;
    }

    /**
     * see festival-schedule.properties description
     * <pre>
     * convert linear structure:
     * ...
     * 2018.new_year.date=0101
     * 2018.new_year.restdays=20171230,20171231
     * 2018.new_year.workdays=
     * ....
     * to hierarchical structure:
     * ...
     * 2018:
     *    new_year:
     *      date:01-01
     *      restdays:2017-12-30,2017-12-31
     *      workdays:
     * ...
     * </pre>
     */
    private static Map<String, Map<String, Map<String, String>>> group(Map<?, ?> properties) {
        return properties.entrySet().stream()
                .map(entry -> new Schedule((String) entry.getKey(), (String) entry.getValue()))
                .collect(
                        Collectors.groupingBy(o -> o.types[0],
                                Collectors.groupingBy(o -> o.types[1],
                                        Collectors.groupingBy(o -> o.types[2],
                                                Collectors.mapping(o -> o.value, CollectorUtils.reducingToLast())))));
    }

    private static class Schedule {
        private String[] types;
        private String value;

        public Schedule(String key, String value) {
            this.types = key.trim().split("\\.");
            this.value = value.trim();
        }
    }

    private static FestivalSchedule resolveFestivalSchedule(Map.Entry<String, Map<String, String>> entry) {
        FestivalSchedule festivalSchedule = new FestivalSchedule();
        festivalSchedule.setFestival(Festival.valueOf(entry.getKey().toUpperCase()));
        festivalSchedule.setDate(MonthDay.parse(entry.getValue().get(FestivalSchedule.PROPERTY_DATE), DateTimeFormatterUtils.SHORT_MONTH_DAY));
        festivalSchedule.setWorkdays(resolveTemporal(entry.getValue().get(FestivalSchedule.PROPERTY_WORKDAYS)));
        festivalSchedule.setRestdays(resolveTemporal(entry.getValue().get(FestivalSchedule.PROPERTY_RESTDAYS)));
        return festivalSchedule;
    }

    private static TemporalAccessor[] resolveTemporal(String value) {
        if (StringUtils.isEmpty(value)) return CollectionUtils.emptyArray(TemporalAccessor.class);
        String[] days = value.split(",");
        return Arrays.stream(days).map(String::trim)
                .map(day -> day.length() == 4
                        ? MonthDay.parse(day, DateTimeFormatterUtils.SHORT_MONTH_DAY)
                        : LocalDate.parse(day, DateTimeFormatterUtils.SHORT_DATE))
                .toArray(TemporalAccessor[]::new);
    }

    public static String buildPropertyKey(Year year, Festival festival, String name) {
        return year.getValue() + "." + festival.name().toLowerCase() + "." + name;
    }

    public static FestivalScheduleProvider buildFestivalScheduleProvider() {
        GroupFestivalScheduleProvider provider = new GroupFestivalScheduleProvider();
        CachedFestivalScheduleProvider cachedFestivalScheduleProvider = new CachedFestivalScheduleProvider();
        PropertiesFestivalScheduleProvider propertiesFestivalScheduleProvider = new PropertiesFestivalScheduleProvider();
        propertiesFestivalScheduleProvider.setFestivalScheduleRegistrar(cachedFestivalScheduleProvider);
        Optional.ofNullable(System.getProperty("festival-schedule.properties")).ifPresent(propertiesFestivalScheduleProvider::setName);
        provider.setFestivalScheduleProviders(Arrays.asList(cachedFestivalScheduleProvider, propertiesFestivalScheduleProvider));
        provider.setFestivalScheduleRegistrar(cachedFestivalScheduleProvider);
        return provider;
    }

}
