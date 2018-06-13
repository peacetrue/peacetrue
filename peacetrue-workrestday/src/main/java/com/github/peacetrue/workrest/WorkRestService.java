package com.github.peacetrue.workrest;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;

/**
 * a service to manage workday and restday
 * //TODO I need a concept to describe work and rest
 *
 * @author xiayx
 */
public interface WorkRestService {

    /**
     * check if the special date is workday
     *
     * @param localDate the local date
     * @return true if it is workday, otherwise false
     */
    boolean isWorkday(LocalDate localDate);

    /**
     * check if the special date is restday
     *
     * @param localDate the local date
     * @return true if it is restday, otherwise false
     */
    boolean isRestday(LocalDate localDate);

    /**
     * get all workdays in special year
     *
     * @param year the year
     * @return the workday list
     */
    List<MonthDay> getWorkdays(Year year);

    /**
     * get all restdays in special year
     *
     * @param year the year
     * @return the restday list
     */
    List<MonthDay> getRestdays(Year year);

}
