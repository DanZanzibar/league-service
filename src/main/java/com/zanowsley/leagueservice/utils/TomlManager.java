package com.zanowsley.leagueservice.utils;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.zanowsley.leagueservice.organization.LeagueConfig;

import java.io.File;
import java.io.IOException;

public class TomlManager {

    private final File file;

    public TomlManager(String filePath) {
        this.file = new File(filePath);
    }

    public synchronized LeagueConfig getData() {
        return new Toml().read(file).to(LeagueConfig.class);
    }

    public synchronized void saveData(LeagueConfig config) throws IOException {
        TomlWriter tomlWriter = new TomlWriter();
        tomlWriter.write(config, file);
    }
}