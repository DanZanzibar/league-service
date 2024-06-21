package com.zanowsley.leagueservice.utils;

import com.zanowsley.leagueservice.exceptions.LeagueDirectoryCreationFailedException;
import com.zanowsley.leagueservice.exceptions.LeagueNotFoundException;
import com.zanowsley.leagueservice.organization.LeagueConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AllLeaguesData {

    private List<LeagueData> leagueDataList;

    public AllLeaguesData() {
        leagueDataList = new ArrayList<>();

        for (String leagueName : getLeagueNames())
            leagueDataList.add(new LeagueData(leagueName));
    }

    public LeagueData getLeagueData(String leagueName) throws LeagueNotFoundException {
        LeagueData matchingLeagueData = null;

        for (LeagueData leagueData : leagueDataList)
            if (leagueData.getLeagueName().equals(leagueName)) {
               matchingLeagueData = leagueData;
               break;
            }

        if (matchingLeagueData == null)
            throw new LeagueNotFoundException(leagueName);

        return matchingLeagueData;
    }

    public void addNewLeague(String leagueName, long maxBookings, String dayOfMatches, String timeOfMatches,
                             String dayBookingOpens, String timeBookingOpens, String dayBookingCloses,
                             String timeBookingCloses) throws IOException, LeagueDirectoryCreationFailedException {
        LeagueConfig config = new LeagueConfig(maxBookings, dayOfMatches, timeOfMatches, dayBookingOpens,
                timeBookingOpens, dayBookingCloses, timeBookingCloses);
        saveNewLeague(leagueName, config);
    }

    public void saveNewLeague(String leagueName, LeagueConfig config) throws IOException,
            LeagueDirectoryCreationFailedException {
        LeagueData leagueData = new LeagueData(leagueName);
        makeLeagueDirectory(leagueName);
        leagueData.saveConfig(config);
        leagueDataList.add(leagueData);
    }

    public List<String> getLeagueNames() {
        File dataDir = new File(FilePaths.DATA_DIR);
        File[] leagueDirs = dataDir.listFiles(File::isDirectory);

        List<String> leagueNames = new ArrayList<>();
        if (leagueDirs != null)
            for (File leagueDir : leagueDirs)
                leagueNames.add(leagueDir.getName());

        return leagueNames;
    }

    private void makeLeagueDirectory(String leagueName) throws LeagueDirectoryCreationFailedException {
        File leagueDir = new File(FilePaths.leagueDataDirPath(leagueName));
        boolean isCreated = leagueDir.mkdir();
        if (!isCreated)
            throw new LeagueDirectoryCreationFailedException(leagueName);
    }
}
