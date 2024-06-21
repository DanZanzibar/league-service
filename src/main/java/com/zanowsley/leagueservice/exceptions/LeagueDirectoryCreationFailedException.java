package com.zanowsley.leagueservice.exceptions;

public class LeagueDirectoryCreationFailedException extends Exception {

    public LeagueDirectoryCreationFailedException(String leagueName) {
        super(String.format("The league '%s' directory was unable to be created.", leagueName));
    }
}
