package com.zanowsley.leagueservice.player;

public class Player {

    public static String CSV_DELIMITER = ",";

    private final String username;
    private final String name;
    private final String email;
    private final boolean isActive;

    public Player(String username, String name, String email, boolean isActive) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.isActive = isActive;
    }

    public Player(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.isActive = true;
    }

    public static Player fromCSVFormat(String csvRow) {
        String[] csvRowEntries = csvRow.split(CSV_DELIMITER);
        String username = csvRowEntries[0];
        String name = csvRowEntries[1];
        String email = csvRowEntries[2];
        boolean isActive = csvRowEntries[3].equals("true");

        return new Player(username, name, email, isActive);
    }

    public String toCSVFormat() {
        String active = (isActive) ? "true" : "false";
        return username + CSV_DELIMITER + name + CSV_DELIMITER + email + CSV_DELIMITER + active;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }
}
