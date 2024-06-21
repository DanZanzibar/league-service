package com.zanowsley.leagueservice.utils;

import com.zanowsley.leagueservice.history.BookingCollection;

import java.io.IOException;

public class SeasonData {

    private final int year;
    private final DataManager bookingsDataManager;

    public SeasonData(String leagueName, int year) {
        this.year = year;

        this.bookingsDataManager = new DataManager(FilePaths.bookingsFilePath(leagueName, year));
    }

    public int getYear() {
        return year;
    }

    public void saveSeasonData(BookingCollection bookingCollection) throws IOException {
        bookingsDataManager.saveData(bookingCollection.toCSVFormat());
    }

    public DataManager getBookingsDataManager() {
        return bookingsDataManager;
    }
}
