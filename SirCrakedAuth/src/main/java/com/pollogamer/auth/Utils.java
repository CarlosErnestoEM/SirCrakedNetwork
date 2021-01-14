package com.pollogamer.auth;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Utils {

    private static Principal plugin;

    public Utils(Principal plugin) {
        this.plugin = plugin;
        init();
    }

    public static Location spawn;

    public static void setSpawn(Location l) {
        plugin.getConfig().set("localizaciones.spawn", com.pollogamer.sircrakedserver.utils.Utils.serializeLoc(l));
        plugin.saveConfig();
        init();
    }

    public static void setCleanPlayer(Player p) {
        p.setMaxHealth(2);
        p.setHealth(p.getMaxHealth());
        p.setGameMode(GameMode.ADVENTURE);
        p.setFoodLevel(20);
        p.getInventory().clear();
    }

    public static void init() {
        try {
            spawn = com.pollogamer.sircrakedserver.utils.Utils.deserializeLoc(plugin.getConfig().getString("localizaciones.spawn"));
        } catch (Exception e) {
            plugin.getLogger().info("Marca el spawn");
        }
    }
}
