package com.zanowsley.leagueservice.utils;

public class FilePaths {

    public static final String DATA_DIR = "/home/zan/sync-general/bin/league-data/";

    public static String leagueDataDirPath(String leagueName) {
        return DATA_DIR + leagueName + "/";
    }

    public static String leagueAllSeasonsDirPath(String leagueName) {
        return leagueDataDirPath(leagueName) + "seasons/";
    }

    public static String leagueSeasonDirPath(String leagueName, int year) {
        return leagueAllSeasonsDirPath(leagueName) + "/" + year + "/";
    }

    public static String leagueTomlPath(String leagueName) {
        return leagueDataDirPath(leagueName) + "config.toml";
    }

    public static String playersFilePath(String leagueName) {
        return leagueDataDirPath(leagueName) + "players.csv";
    }

    public static String bookingsFilePath(String leagueName, int year) {
        return leagueAllSeasonsDirPath(leagueName) + year + "/bookings.csv";
    }
}
