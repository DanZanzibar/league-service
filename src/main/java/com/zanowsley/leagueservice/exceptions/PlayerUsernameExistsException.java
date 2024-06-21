package com.zanowsley.leagueservice.exceptions;

public class PlayerUsernameExistsException extends Exception {

    public PlayerUsernameExistsException(String username) {
        super(String.format("The username '%s' already exists.", username));
    }
}
