package com.pollogamer.sircrakedffa.manager;

import com.pollogamer.sircrakedffa.Principal;
import com.pollogamer.sircrakedserver.utils.Utils;
import org.bukkit.Location;

public class Lang {

    private static Principal plugin;
    public static Location ffa;
    public static Location buildffa;
    public static Location comboffa;

    public Lang(Principal plugin) {
        this.plugin = plugin;
        init();
    }

    public static void init() {
        ffa = Utils.deserializeLoc(plugin.getConfig().getString("spawns.ffa"));
        buildffa = Utils.deserializeLoc(plugin.getConfig().getString("spawns.buildffa"));
        comboffa = Utils.deserializeLoc(plugin.getConfig().getString("spawns.comboffa"));
    }

}
