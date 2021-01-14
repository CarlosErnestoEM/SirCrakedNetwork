package com.pollogamer.tntrun.extras;

import com.pollogamer.tntrun.Principal;
import org.bukkit.Location;

public class Lang {

    Principal plugin = Principal.plugin;

    public Lang() {
        init();
    }

    public static boolean started;
    public static boolean starting;
    public static boolean forcestart;
    public static boolean pvprun;
    public static boolean tnt;
    public static int minplayers;
    public static Location spawn;
    public static Location spawnlobby;

    public void init() {
        started = false;
        starting = false;
        forcestart = false;
        tnt = false;
        try {
            pvprun = plugin.getConfig().getBoolean("pvprun");
            minplayers = plugin.getConfig().getInt("minplayers");
            spawn = Utils.deserializeLoc(Principal.plugin.getConfig().getString("locations.spawn"));
            spawnlobby = Utils.deserializeLoc(Principal.plugin.getConfig().getString("locations.lobby"));
        } catch (Exception e) {
            Principal.plugin.getLogger().info("Por favor revisa la configuracion o marca el spawn y lobby");
        }
    }

}