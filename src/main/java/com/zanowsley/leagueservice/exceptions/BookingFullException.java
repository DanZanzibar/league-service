package com.zanowsley.leagueservice.exceptions;

import java.time.LocalDate;

public class BookingFullException extends Exception {

    public BookingFullException(LocalDate eventDate) {
        super(String.format("Booking if full for %s.", eventDate.toString()));
    }
}
