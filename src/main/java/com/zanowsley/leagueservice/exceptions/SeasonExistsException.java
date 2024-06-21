package com.zanowsley.leagueservice.exceptions;

public class SeasonExistsException extends Exception {

    public SeasonExistsException(int year) {
        super(String.format("The %s season already exists.", year));
    }
}
