package com.zanowsley.leagueservice.exceptions;

public class LeagueNotFoundException extends Exception {

    public LeagueNotFoundException(String leagueName) {
        super(String.format("League '%s' not found.", leagueName));
    }
}
