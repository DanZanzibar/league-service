package com.zanowsley.leagueservice.records;

public record NewPlayer(long resultCode, String message) {

    public static final int SUCCESS = 0;
    public static final int FAILED_USERNAME_EXISTS = 1;
    public static final int FAILED_NAME_EXISTS = 2;
    public static final int FAILED_EMAIL_EXISTS = 3;

    public static final String MSG_SUCCESS = "%s has been added.";
    public static final String MSG_USERNAME_EXISTS = "The username %s already exists.";
    public static final String MSG_NAME_EXISTS = "The name %s already exists.";
    public static final String MSG_EMAIL_EXISTS = "The email %s already exists.";
}
