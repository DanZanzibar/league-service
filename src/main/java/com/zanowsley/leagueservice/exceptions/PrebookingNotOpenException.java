package com.zanowsley.leagueservice.exceptions;

import java.time.LocalDate;

public class PrebookingNotOpenException extends Exception {

    public PrebookingNotOpenException(LocalDate eventDate) {
        super(String.format("Prebooking not open for %s.", eventDate.toString()));
    }
}
