package com.zanowsley.leagueservice.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class TimeOfWeek {

    private final DayOfWeek dayOfWeek;
    private final LocalTime timeOfDay;

    public TimeOfWeek(DayOfWeek dayOfWeek, LocalTime timeOfDay) {
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    public TimeOfWeek(String dayOfWeekStr, String timeOfDayStr) {
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeekStr);
        this.timeOfDay = LocalTime.parse(timeOfDayStr);
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalDateTime nearestBefore(LocalDateTime dateTime) {
        LocalTime referenceTime = dateTime.toLocalTime();
        LocalDate referenceDate = dateTime.toLocalDate();

        LocalDate nearestDate;
        if (timeOfDay.isBefore(referenceTime))
            nearestDate = referenceDate.with(TemporalAdjusters.previousOrSame(dayOfWeek));
        else
            nearestDate = referenceDate.with(TemporalAdjusters.previous(dayOfWeek));

        return LocalDateTime.of(nearestDate, timeOfDay);
    }
}
