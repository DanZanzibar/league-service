package com.zanowsley.leagueservice.exceptions;

public class PlayerNameExistsException extends Exception {

    public PlayerNameExistsException(String message) {
        super(String.format("The name '%s' already exists."));
    }
}
