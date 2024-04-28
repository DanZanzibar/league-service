package com.zanowsley.leagueservice.player;

import com.zanowsley.leagueservice.LeagueData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
            if (!row.isEmpty())
                players.add(Player.fromCSVFormat(row));

        return new PlayerCollection(players);
    }

    public String toCSVFormat() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Player player : players)
            stringBuilder.append(player.toCSVFormat()).append(ROW_DELIMITER);

        return stringBuilder.toString();
    }

    public boolean isUsernameUsed(String username) {
        return isPlayerParameterUsed(Player::getUsername, username);
    }

    public boolean isNameUsed(String name) {
        return isPlayerParameterUsed(Player::getName, name);
    }

    public boolean isEmailUsed(String email) {
        return isPlayerParameterUsed(Player::getEmail, email);
    }

    private boolean isPlayerParameterUsed(Function<Player, String> parameterGetter, String value) {
        boolean isUsed = false;

        for (Player player : players)
            if (parameterGetter.apply(player).equals(value)) {
                isUsed = true;
                break;
            }

        return isUsed;
    }

    public void addNewPlayer(LeagueData leagueData, String playerName, String email) {
        
    }
}
