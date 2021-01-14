package com.pollogamer.sircrakedserver.manager;

import com.pollogamer.SirCrakedCore;

import java.io.File;

/**
 * Created by Carlos on 05/05/2017.
 */
public class ConfigManager {

    public static void loadConfig() {
        if (!SirCrakedCore.getCore().getDataFolder().exists()) {
            SirCrakedCore.getCore().getDataFolder().mkdirs();
        }

        File cfile = new File(SirCrakedCore.getCore().getDataFolder(), "config.yml");
        if (!cfile.exists()) {
            SirCrakedCore.getCore().saveResource("config.yml", false);
        }
    }
}
