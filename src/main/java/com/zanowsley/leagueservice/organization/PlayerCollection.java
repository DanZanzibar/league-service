package com.zanowsley.leagueservice.organization;

import com.zanowsley.leagueservice.exceptions.PlayerEmailExistsException;
import com.zanowsley.leagueservice.exceptions.PlayerNameExistsException;
import com.zanowsley.leagueservice.exceptions.PlayerNotFoundException;
import com.zanowsley.leagueservice.exceptions.PlayerUsernameExistsException;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PlayerCollection implements Iterable<Player>{

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

    public PlayerCollection withOnlyActivePlayers() {
        List<Player> activePlayers = new ArrayList<>();

        for (Player player : players)
            if (player.isActive())
                activePlayers.add(player);

        return new PlayerCollection(activePlayers);
    }

    public boolean isUsernameUsed(String username) {
        return isPlayerParameterUsed(Player::username, username);
    }

    public boolean isNameUsed(String name) {
        return isPlayerParameterUsed(Player::name, name);
    }

    public boolean isEmailUsed(String email) {
        return isPlayerParameterUsed(Player::email, email);
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

    public Player getPlayerByUsername(String username) throws PlayerNotFoundException {
        Player playerToLookup = null;

        for (Player player : players)
            if (player.username().equals(username)) {
                playerToLookup = player;
                break;
            }

        if (playerToLookup == null)
            throw new PlayerNotFoundException(username);

        return playerToLookup;
    }

    public void addPlayer(String username, String name, String email) throws PlayerUsernameExistsException,
            PlayerNameExistsException, PlayerEmailExistsException {

        if (isUsernameUsed(username))
            throw new PlayerUsernameExistsException(username);
        if (isNameUsed(name))
            throw new PlayerNameExistsException(name);
        if (isEmailUsed(email))
            throw new PlayerEmailExistsException(email);

        players.add(new Player(username, name, email, true));
    }

    public void modifyPlayerName(String username, String name) throws PlayerNotFoundException,
            PlayerNameExistsException {
        Player playerToModify = getPlayerByUsername(username);

        if (isNameUsed(name))
            throw new PlayerNameExistsException(name);

        Player modifiedPlayer = playerToModify.withName(name);
        removePlayer(playerToModify);
        addPlayer(modifiedPlayer);
    }

    public void modifyPlayerEmail(String username, String email) throws PlayerNotFoundException,
            PlayerEmailExistsException {
        Player playerToModify = getPlayerByUsername(username);

        if (isEmailUsed(email))
            throw new PlayerEmailExistsException(email);

        Player modifiedPlayer = playerToModify.withEmail(email);
        removePlayer(playerToModify);
        addPlayer(modifiedPlayer);
    }

    public void modifyPlayerIsActive(String username, boolean isActive) throws PlayerNotFoundException {
        Player playerToModify = getPlayerByUsername(username);
        Player modifiedPlayer = playerToModify.withIsActive(isActive);
        removePlayer(playerToModify);
        addPlayer(modifiedPlayer);
    }

    private void addPlayer(Player player) {
        players.add(player);
    }

    private void removePlayer(Player player) {
        players.remove(player);
    }

    public int size() {
        return players.size();
    }

    @NonNull
    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }

    public void sortByName() {
        Comparator<Player> comparator = Comparator.comparing(Player::name);
        players.sort(comparator);
    }
}
