package com.zanowsley.leagueservice.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {

    public static int DAYS_BEFORE_BOOKING_OPENS = 5;
    public static LocalTime TIME_BOOKING_OPENS = LocalTime.of(18, 0);
    public static LocalTime TIME_BOOKING_CLOSES = LocalTime.of(16, 0);

    private final LocalDate date;
    private List<Integer> bookedPlayers;
    private List<Integer> waitListPlayers;

    public Booking(LocalDate date, List<Integer> bookedPlayers, List<Integer> waitListPlayers) {
        this.date = date;
        this.bookedPlayers = bookedPlayers;
        this.waitListPlayers = waitListPlayers;
    }

    public Booking(LocalDate date) {
        this.date = date;
        this.bookedPlayers = new ArrayList<>(8);
        this.waitListPlayers = new ArrayList<>();
    }

    public boolean hasBookingOpened() {
        LocalDate dateBookingOpens = date.minusDays(DAYS_BEFORE_BOOKING_OPENS);
        LocalDateTime dateTimeBookingOpens = LocalDateTime.of(dateBookingOpens, TIME_BOOKING_OPENS);
        LocalDateTime dateTimeNow = LocalDateTime.now();

        return dateTimeNow.isAfter(dateTimeBookingOpens);
    }

    public boolean hasBookingClosed() {
        LocalDateTime dateTimeBookingCloses = LocalDateTime.of(date, TIME_BOOKING_CLOSES);
        LocalDateTime dateTimeNow = LocalDateTime.now();

        return dateTimeNow.isAfter(dateTimeBookingCloses);
    }

    public boolean isBookingAvailable() {
        return bookedPlayers.size() < 8;
    }

    public void addToBooked(int playerId) {

    }
}
