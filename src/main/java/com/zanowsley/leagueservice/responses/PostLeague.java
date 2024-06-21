package com.zanowsley.leagueservice.responses;

public record PostLeague(int resultCode, String message) {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAILED = 1;

    public static final String MSG_SUCCESS = "League '%s' created.";
}
