package com.zanowsley.leagueservice;



import com.zanowsley.leagueservice.player.PlayerCollection;
import com.zanowsley.leagueservice.utils.Config;
import com.zanowsley.leagueservice.utils.FileStorageManager;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;

@Component
public class LeagueData {

    private FileStorageManager playerStorageManager;

    public LeagueData() {
        this.playerStorageManager = new FileStorageManager(new File(Config.PLAYER_FILE));
    }

    public PlayerCollection getPlayerCollection() {
        String playerCsvData;

        try {
            playerCsvData = playerStorageManager.readFromFile();
        } catch (FileNotFoundException e) {
            playerStorageManager.writeToFile("");
            playerCsvData = "";
        }

        return PlayerCollection.fromCSVFormat(playerCsvData);
    }
}
