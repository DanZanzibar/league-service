package com.zanowsley.leagueservice.responses;

public record PostPlayer(int resultCode, String message) {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_USERNAME_EXISTS = 1;
    public static final int CODE_NAME_EXISTS = 2;
    public static final int CODE_EMAIL_EXISTS = 3;

    public static final String MSG_SUCCESS = "%s has been added.";
}
