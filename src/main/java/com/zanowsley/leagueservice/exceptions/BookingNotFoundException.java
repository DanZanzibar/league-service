package com.zanowsley.leagueservice.exceptions;

import java.time.LocalDate;

public class BookingNotFoundException extends Exception {

    public BookingNotFoundException(LocalDate eventDate) {
        super(String.format("No Booking for date %s.", eventDate.toString()));
    }
}
