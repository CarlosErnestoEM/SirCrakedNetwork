package com.pollogamer.uhcsimulator.extras;

import com.pollogamer.sircrakedserver.utils.Utils;
import com.pollogamer.uhcsimulator.Principal;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Lang {

    public Lang() {
        init();
    }

    public static int bordersize;
    public static int nextborder = 75;
    public static Location lobby;
    public static int minplayers;
    public static boolean setup;
    public static boolean starting = false;
    public static boolean started = false;
    public static boolean forcestart = false;
    public static List<Player> players = new ArrayList<>();
    public static List<Player> spectators = new ArrayList<>();

    public void init() {
        try {
            setup = Principal.getPlugin().getConfig().getBoolean("setup");
            bordersize = Principal.getPlugin().getConfig().getInt("bordersize");
            minplayers = Principal.getPlugin().getConfig().getInt("minplayers");
            lobby = Utils.deserializeLoc(Principal.getPlugin().getConfig().getString("locations.spawn"));
        } catch (Exception e) {
            Principal.getPlugin().log("Please set the lobby location!");
        }
    }
}
