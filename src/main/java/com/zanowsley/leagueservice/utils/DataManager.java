package com.zanowsley.leagueservice.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {

    private final File file;

    //--------------------------------------------------------------------------
    //  A constructor for the class.
    //--------------------------------------------------------------------------
    public DataManager(String filePath) {
        this.file = new File(filePath);
    }

    //--------------------------------------------------------------------------
    //  This method returns a 'String' from the managed file.
    //--------------------------------------------------------------------------
    public synchronized String getData() throws IOException {
        StringBuilder data = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        }

        return data.toString();
    }

    //--------------------------------------------------------------------------
    //  This method writes a 'String' to the managed file.
    //--------------------------------------------------------------------------
    public synchronized void saveData(String data) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write(data + "\n");
        writer.close();
    }
}
