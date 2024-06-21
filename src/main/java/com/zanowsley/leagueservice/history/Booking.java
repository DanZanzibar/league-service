package com.zanowsley.leagueservice.history;

import com.zanowsley.leagueservice.organization.LeagueConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {

    public static final String CSV_DELIMITER = ",";

    private final long maxBookings;
    private final LocalDateTime eventDateTime;
    private final LocalDateTime prebookingOpensDateTime;
    private final LocalDateTime bookingOpensDateTime;
    private final LocalDateTime bookingClosesDateTime;
    private List<String> bookedPlayers;
    private List<String> waitListedPlayers;

    public Booking(long maxBookings, LocalDateTime eventDateTime, LocalDateTime prebookingOpensDateTime,
                   LocalDateTime bookingOpensDateTime, LocalDateTime bookingClosesDateTime,
                   List<String> bookedPlayers, List<String> waitListedPlayers) {
        this.maxBookings = maxBookings;
        this.eventDateTime = eventDateTime;
        this.prebookingOpensDateTime = prebookingOpensDateTime;
        this.bookingOpensDateTime = bookingOpensDateTime;
        this.bookingClosesDateTime = bookingClosesDateTime;

        this.bookedPlayers = bookedPlayers;
        this.waitListedPlayers = waitListedPlayers;
    }

    public Booking(LocalDateTime eventDateTime, LeagueConfig leagueConfig) {
        if (!eventDateTime.getDayOfWeek().equals(leagueConfig.getMatchesTimeOfWeek().getDayOfWeek()))
            throw new IllegalArgumentException("Invalid event date for this league.");

        this.maxBookings = leagueConfig.getMaxBookings();
        this.eventDateTime = eventDateTime;
        this.prebookingOpensDateTime = leagueConfig.getPrebookingOpensDateTime(eventDateTime);
        this.bookingOpensDateTime = leagueConfig.getBookingOpensDateTime(eventDateTime);
        this.bookingClosesDateTime = leagueConfig.getBookingClosesDateTime(eventDateTime);

        this.bookedPlayers = new ArrayList<>((int) leagueConfig.getMaxBookings());
        this.waitListedPlayers = new ArrayList<>();
    }

    public static Booking fromCSVFormat(String csvRow) {
        String[] csvRowEntries = csvRow.split(CSV_DELIMITER, -1);
        LocalDateTime eventDateTime = LocalDateTime.parse(csvRowEntries[0]);
        int maxBookings = Integer.parseInt(csvRowEntries[1]);
        LocalDateTime prebookingOpensDateTime = LocalDateTime.parse(csvRowEntries[2]);
        LocalDateTime bookingOpensDateTime = LocalDateTime.parse(csvRowEntries[3]);
        LocalDateTime bookingClosesDateTime = LocalDateTime.parse(csvRowEntries[4]);

        int nextIndex = 5;
        List<String> bookedPlayers = new ArrayList<>(maxBookings);
        while (nextIndex < 5 + maxBookings) {
            String nextEntry = csvRowEntries[nextIndex];
            if (!nextEntry.isEmpty())
                bookedPlayers.add(nextEntry);
            nextIndex++;
        }

        List<String> waitListPlayers = new ArrayList<>();
        while (nextIndex < csvRowEntries.length) {
            String nextEntry = csvRowEntries[nextIndex];
            if (!nextEntry.isEmpty())
                waitListPlayers.add(nextEntry);
            nextIndex++;
        }

        return new Booking(maxBookings, eventDateTime, prebookingOpensDateTime, bookingOpensDateTime,
                bookingClosesDateTime, bookedPlayers, waitListPlayers);
    }

    public String toCSVFormat() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(maxBookings).append(CSV_DELIMITER);
        stringBuilder.append(eventDateTime.toString()).append(CSV_DELIMITER);
        stringBuilder.append(prebookingOpensDateTime.toString()).append(CSV_DELIMITER);
        stringBuilder.append(bookingOpensDateTime.toString()).append(CSV_DELIMITER);
        stringBuilder.append(bookingClosesDateTime.toString()).append(CSV_DELIMITER);

        for (int i = 0; i < maxBookings; i++) {
            if (i < bookedPlayers.size())
                stringBuilder.append(bookedPlayers.get(i));
            stringBuilder.append(CSV_DELIMITER);
        }

        for (int i = 0; i < waitListedPlayers.size(); i++) {
            stringBuilder.append(waitListedPlayers.get(i));
            if (i != waitListedPlayers.size() - 1)
                stringBuilder.append(CSV_DELIMITER);
        }

        return stringBuilder.toString();
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public boolean isPrebookingOpen() {
        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(prebookingOpensDateTime) && now.isBefore(bookingOpensDateTime);
    }

    public boolean isBookingOpen() {
        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(bookingOpensDateTime) && now.isBefore(bookingClosesDateTime);
    }

    public boolean isBookingSpotAvailable() {
        return bookedPlayers.size() < maxBookings;
    }

    public void addPlayerToBooked(String username) {
        bookedPlayers.add(username);
    }
}
