package com.zanowsley.leagueservice;



import com.zanowsley.leagueservice.utils.Config;
import com.zanowsley.leagueservice.utils.FileStorageManager;

import java.io.File;

public class LeagueData {

    private FileStorageManager playerStorageManager;

    public LeagueData() {
        this.playerStorageManager = new FileStorageManager(new File(Config.PLAYER_FILE));
    }

    public FileStorageManager getPlayerStorageManager() {
        return playerStorageManager;
    }
}
