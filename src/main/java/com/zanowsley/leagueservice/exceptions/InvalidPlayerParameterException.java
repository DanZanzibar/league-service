package com.zanowsley.leagueservice.exceptions;

public class InvalidPlayerParameterException extends Exception {

    public InvalidPlayerParameterException() {
        super("Only 'name', 'email', and 'isActive' are valid player parameters to modify.");
    }
}
