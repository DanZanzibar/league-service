package com.zanowsley.leagueservice.exceptions;

public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(String username) {
        super(String.format("Player '%s' not found.", username));
    }
}
