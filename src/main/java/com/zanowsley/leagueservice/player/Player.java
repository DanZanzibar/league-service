package com.zanowsley.leagueservice.player;

public class Player {

    public static String CSV_DELIMITER = ",";

    private final int id;
    private final String name;
    private final String email;
    private final boolean isActive;

    public Player(int id, String name, String email, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isActive = isActive;
    }

    public Player(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isActive = true;
    }

    public static Player fromCSVFormat(String csvRow) {
        String[] csvRowEntries = csvRow.split(CSV_DELIMITER);
        int id = Integer.parseInt(csvRowEntries[0]);
        String name = csvRowEntries[1];
        String email = csvRowEntries[2];
        boolean isActive = csvRowEntries[3].equals("true");

        return new Player(id, name, email, isActive);
    }

    public String toCSVFormat() {
        String active = (isActive) ? "true" : "false";
        return String.valueOf(id) + CSV_DELIMITER + name + CSV_DELIMITER + email + CSV_DELIMITER + active;
    }
}
