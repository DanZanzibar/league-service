package com.zanowsley.leagueservice.exceptions;

import java.time.LocalDate;

public class BookingNotOpenException extends Exception {

    public BookingNotOpenException(LocalDate eventDate) {
        super(String.format("Booking is not open for %s.", eventDate.toString()));
    }
}
