package com.zanowsley.leagueservice.organization;

public record Player(String username, String name, String email, boolean isActive) {

    public static final String CSV_DELIMITER = ",";

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

    public Player withName(String name) {
        return new Player(username, name, email, isActive);
    }

    public Player withEmail(String email) {
        return new Player(username, name, email, isActive);
    }

    public Player withIsActive(boolean isActive) {
        return new Player(username, name, email, isActive);
    }
}
