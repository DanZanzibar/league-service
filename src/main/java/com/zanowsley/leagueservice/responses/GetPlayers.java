package com.zanowsley.leagueservice.responses;

import com.zanowsley.leagueservice.organization.PlayerCollection;

public record GetPlayers(int resultCode, String message, PlayerCollection playerCollection) {

    public static final int CODE_SUCCESS = 0;

    public static final String MSG_SUCCESS = "Players in league '%s'.";
}
