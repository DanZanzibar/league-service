package com.zanowsley.leagueservice.exceptions;

public class PlayerEmailExistsException extends Exception {

    public PlayerEmailExistsException(String email) {
        super(String.format("The email '%s' already exists.", email));
    }
}
