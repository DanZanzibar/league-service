package com.zanowsley.leagueservice.utils;

//import com.zanowsley.leagueservice.history.BookingCollection;
import com.zanowsley.leagueservice.exceptions.*;
import com.zanowsley.leagueservice.history.BookingCollection;
import com.zanowsley.leagueservice.organization.LeagueConfig;
import com.zanowsley.leagueservice.organization.Player;
import com.zanowsley.leagueservice.organization.PlayerCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeagueData {

    private final String leagueName;
    private final TomlManager configManager;
    private final DataManager playersData;
    private List<SeasonData> seasonsData;

    public LeagueData(String leagueName) {
        this.leagueName = leagueName;
        this.configManager = new TomlManager(FilePaths.leagueTomlPath(leagueName));
        this.playersData = new DataManager(FilePaths.playersFilePath(leagueName));

        this.seasonsData = new ArrayList<>();
        File leagueSeasonDir = new File(FilePaths.leagueAllSeasonsDirPath(leagueName));

        File[] seasonDirs = leagueSeasonDir.listFiles(File::isDirectory);

        if (seasonDirs != null)
            for (File seasonDir : seasonDirs) {
                int year = Integer.parseInt(seasonDir.getName());
                seasonsData.add(new SeasonData(leagueName, year));
            }
    }

    public String getLeagueName() {
        return leagueName;
    }

    public LeagueConfig getConfig() {
        return configManager.getData();
    }

    public void saveConfig(LeagueConfig config) throws IOException {
        configManager.saveData(config);
    }

    public PlayerCollection getPlayerCollection() throws IOException {
        String playerCsvData = playersData.getData();

        return PlayerCollection.fromCSVFormat(playerCsvData);
    }

    public void savePlayerCollection(PlayerCollection playerCollection) throws IOException {
        playersData.saveData(playerCollection.toCSVFormat());
    }

    public BookingCollection getBookingCollection(int year) throws IOException, SeasonNotFoundException {
        SeasonData seasonData = getSeasonData(year);
        String bookingsDataStr = seasonData.getBookingsDataManager().getData();

        return BookingCollection.fromCSVFormat(bookingsDataStr);
    }

    public void initNewSeason(int year) throws IOException, SeasonExistsException, SeasonCreationFailedException {
        File seasonDir = new File(FilePaths.leagueSeasonDirPath(leagueName, year));
        if (seasonDir.exists())
            throw new SeasonExistsException(year);

        boolean isCreated = seasonDir.mkdir();
        if (!isCreated)
            throw new SeasonCreationFailedException(year);

        SeasonData seasonData = new SeasonData(leagueName, year);
        BookingCollection bookingCollection = BookingCollection.fromNewSeason(configManager.getData(), year);
        seasonData.saveSeasonData(bookingCollection);
    }

    public void addNewPlayer(String username, String name, String email) throws IOException,
            PlayerUsernameExistsException, PlayerNameExistsException, PlayerEmailExistsException {
        PlayerCollection playerCollection = getPlayerCollection();
        playerCollection.addPlayer(username, name, email);
        playerCollection.sortByName();
        savePlayerCollection(playerCollection);
    }

    public void modifyPlayer(String username, String parameter, String value) throws IOException,
            PlayerNotFoundException, PlayerNameExistsException, PlayerEmailExistsException, IllegalArgumentException {
        PlayerCollection playerCollection = getPlayerCollection();

        if (parameter.equals("name"))
            playerCollection.modifyPlayerName(username, value);

        else if (parameter.equals("email"))
            playerCollection.modifyPlayerEmail(username, value);

        else if (parameter.equals("isActive")) {
            boolean isActive;
            if (value.equals("true"))
                isActive = true;
            else if (value.equals("false"))
                isActive = false;
            else
                throw new IllegalArgumentException("Value passed for 'isActive' must be 'true' or 'false'.");

            playerCollection.modifyPlayerIsActive(username, isActive);
        }
        else
            throw new IllegalArgumentException("Not a valid player parameter. Must be 'name', 'email', or 'isActive'.");

        savePlayerCollection(playerCollection);
    }

    private SeasonData getSeasonData(int year) throws SeasonNotFoundException {
        SeasonData matchingSeasonData = null;

        for (SeasonData seasonData : seasonsData)
            if (seasonData.getYear() == year) {
                matchingSeasonData = seasonData;
                break;
            }

        if (matchingSeasonData == null)
            throw new SeasonNotFoundException(year);

        return matchingSeasonData;
    }
}
