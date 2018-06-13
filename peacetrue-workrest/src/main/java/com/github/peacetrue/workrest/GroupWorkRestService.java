package com.github.peacetrue.workrest;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * delegate to a {@link WorkRestService} collection.
 * workday and restday is mutually exclusive.
 *
 * @author xiayx
 */
public class GroupWorkRestService implements WorkRestService {

    /** the last one has the highest priority, so it can overwrite previous */
    private List<WorkRestService> workRestServices;

    @Override
    public boolean isWorkday(LocalDate localDate) {
        for (int i = workRestServices.size() - 1; i >= 0; i--) {
            WorkRestService workRestService = workRestServices.get(i);
            if (workRestService.isWorkday(localDate)) return true;
            if (workRestService.isRestday(localDate)) return false;
        }
        //default to false
        return false;
    }

    @Override
    public boolean isRestday(LocalDate localDate) {
        for (int i = workRestServices.size() - 1; i >= 0; i--) {
            WorkRestService workRestService = workRestServices.get(i);
            if (workRestService.isRestday(localDate)) return true;
            if (workRestService.isWorkday(localDate)) return false;
        }
        //default to false
        return false;
    }

    @Override
    public List<MonthDay> getWorkdays(Year year) {
        List<MonthDay> workdays = new ArrayList<>();
        for (WorkRestService workRestService : workRestServices) {
            workdays.removeAll(workRestService.getRestdays(year));
            workdays.addAll(workRestService.getWorkdays(year));
        }
        workdays.sort(MonthDay::compareTo);
        return workdays;
    }

    @Override
    public List<MonthDay> getRestdays(Year year) {
        List<MonthDay> restdays = new ArrayList<>();
        for (WorkRestService workRestService : workRestServices) {
            restdays.removeAll(workRestService.getWorkdays(year));
            restdays.addAll(workRestService.getRestdays(year));
        }
        restdays.sort(MonthDay::compareTo);
        return restdays;
    }

    public void setWorkRestServices(List<WorkRestService> workRestServices) {
        this.workRestServices = workRestServices;
    }
}
