package com.zanowsley.leagueservice.responses;

import java.time.LocalDate;
import java.util.List;

public record BookingInfo(LocalDate eventDate, List<String> bookedPlayers, List<String> waitlistedPlayers) {

    public static final int CODE_SUCCESS = 0;

    public static final String MSG_SUCCESS = "Booking info for %s.";
}
