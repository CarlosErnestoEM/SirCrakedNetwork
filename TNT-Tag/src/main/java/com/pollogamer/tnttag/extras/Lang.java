package com.pollogamer.tnttag.extras;

import com.pollogamer.tnttag.Principal;
import org.bukkit.Location;

public class Lang {

    Principal plugin;

    public Lang(Principal plugin) {
        this.plugin = plugin;
        init();
    }

    public static boolean started;
    public static boolean starting;
    public static boolean forcestart;
    public static int minplayers;
    public static Location spawn;
    public static Location spawnlobby;

    public void init() {
        started = false;
        starting = false;
        try {
            minplayers = plugin.getConfig().getInt("minplayers");
            forcestart = false;
            spawn = Utils.deserializeLoc(Principal.plugin.getConfig().getString("locations.spawn"));
            spawnlobby = Utils.deserializeLoc(Principal.plugin.getConfig().getString("locations.lobby"));
        } catch (Exception e) {

        }
    }

}
