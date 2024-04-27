package com.zanowsley.leagueservice.player;

import com.zanowsley.leagueservice.LeagueData;

import java.util.ArrayList;
import java.util.List;

public class PlayerCollection {

    public static final String ROW_DELIMITER = "\n";

    private List<Player> players;

    public PlayerCollection(List<Player> players) {
        this.players = players;
    }

    public static PlayerCollection fromCSVFormat(String csvString) {
        List<Player> players = new ArrayList<>();
        String[] csvRows = csvString.split(ROW_DELIMITER);

        for (String row : csvRows)
            players.add(Player.fromCSVFormat(row));

        return new PlayerCollection(players);
    }

    public String toCSVFormat() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Player player : players)
            stringBuilder.append(player.toCSVFormat()).append(ROW_DELIMITER);

        return stringBuilder.toString();
    }

    public void addNewPlayer(LeagueData leagueData, String playerName, String email) {
        
    }
}
