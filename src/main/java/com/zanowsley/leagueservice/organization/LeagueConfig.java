package com.zanowsley.leagueservice.organization;

import com.zanowsley.leagueservice.utils.TimeOfWeek;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class LeagueConfig {

    private final long maxBookings;
    private final String dayOfMatches;
    private final String timeOfMatches;
    private final String dayBookingOpens;
    private final String timeBookingOpens;
    private final String dayBookingCloses;
    private final String timeBookingCloses;

    public LeagueConfig(long maxBookings, String dayOfMatches, String timeOfMatches, String dayBookingOpens,
                        String timeBookingOpens, String dayBookingCloses, String timeBookingCloses) {
        this.maxBookings = maxBookings;
        this.dayOfMatches = dayOfMatches;
        this.timeOfMatches = timeOfMatches;
        this.dayBookingOpens = dayBookingOpens;
        this.timeBookingOpens = timeBookingOpens;
        this.dayBookingCloses = dayBookingCloses;
        this.timeBookingCloses = timeBookingCloses;
    }

    public long getMaxBookings() {
        return maxBookings;
    }

    public TimeOfWeek getMatchesTimeOfWeek() {
        return new TimeOfWeek(dayOfMatches, timeOfMatches);
    }

    public LocalDateTime getNextMatchesDateTime(LocalDateTime dateTime) {
        LocalDate nextDate = dateTime.with(TemporalAdjusters.next(DayOfWeek.valueOf(dayOfMatches))).toLocalDate();

        return LocalDateTime.of(nextDate, LocalTime.parse(timeOfMatches));
    }

    public LocalDateTime getPrebookingOpensDateTime(LocalDateTime eventDateTime) {
        return getBookingOpensDateTime(eventDateTime).minusWeeks(1);
    }

    public LocalDateTime getBookingOpensDateTime(LocalDateTime eventDateTime) {
        return getBookingOpensTimeOfWeek().nearestBefore(eventDateTime);
    }

    public LocalDateTime getBookingClosesDateTime(LocalDateTime eventDateTime) {
        return getBookingClosesTimeOfWeek().nearestBefore(eventDateTime);
    }

    private TimeOfWeek getBookingOpensTimeOfWeek() {
        return new TimeOfWeek(dayBookingOpens, timeBookingOpens);
    }

    private TimeOfWeek getBookingClosesTimeOfWeek() {
        return new TimeOfWeek(dayBookingCloses, timeBookingCloses);
    }
}
