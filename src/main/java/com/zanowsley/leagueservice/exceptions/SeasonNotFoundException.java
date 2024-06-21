package com.zanowsley.leagueservice.exceptions;

public class SeasonNotFoundException extends Exception {

    public SeasonNotFoundException(int year) {
        super(String.format("The season %s has not been created.", year));
    }
}
