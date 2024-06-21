package com.zanowsley.leagueservice.exceptions;

public class PlayerParameterExistsException extends Exception {

    public PlayerParameterExistsException(String parameter, String value) {
        super(String.format("The value '%s' already exists for parameter '%s'", value, parameter));
    }
}
