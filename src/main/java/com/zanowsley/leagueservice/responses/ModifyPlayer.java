package com.zanowsley.leagueservice.responses;

public record ModifyPlayer(int resultCode, String message) {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_PLAYER_NOT_FOUND = 1;
    public static final int CODE_PLAYER_NAME_EXISTS = 2;
    public static final int CODE_PLAYER_EMAIL_EXISTS = 3;

    public static final String MSG_SUCCESS = "%s updated.";
}
