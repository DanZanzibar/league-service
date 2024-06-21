package com.zanowsley.leagueservice.exceptions;

public class SeasonCreationFailedException extends Exception {

    public SeasonCreationFailedException(int year) {
        super(String.format("Creation of season %s failed.", year));
    }
}
