package com.pollogamer.uhc;

import com.pollogamer.uhc.kills.KillsManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Principal extends JavaPlugin {

    private static KillsManager killsManager;
    private static Principal plugin;

    public void onEnable() {
        plugin = this;

    }

    public static int findShort(String s) {
        final int[] minLenght = new int[1];
        minLenght[0] = Integer.MAX_VALUE;
        Arrays.stream(s.split(" ")).forEach(word -> {
            if (minLenght[0] > word.length()) {
                minLenght[0] = word.length();
            }
        });
        return minLenght[0];
    }

    public static KillsManager getKillsManager() {
        return killsManager;
    }

    public static Principal getPlugin() {
        return plugin;
    }
}
