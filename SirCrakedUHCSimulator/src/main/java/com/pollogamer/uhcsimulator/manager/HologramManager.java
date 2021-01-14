package com.pollogamer.uhcsimulator.manager;

import com.pollogamer.sircrakedserver.hologram.Hologram;
import com.pollogamer.sircrakedserver.utils.Utils;
import com.pollogamer.uhcsimulator.Principal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HologramManager {

    private static List<Hologram> holograms = new ArrayList<>();

    public static void putHolograms() {
        if (Principal.getPlugin().getConfig().getConfigurationSection("holograms.topelo") != null) {
            int topelo = 0;
            for (String s : Principal.getPlugin().getConfig().getConfigurationSection("holograms.topelo").getKeys(false)) {
                Hologram hologram = new Hologram("topelo" + topelo++, Utils.deserializeLoc(Principal.getPlugin().getConfig().getString("holograms.topelo." + s)), getLinesTop("&6Top 10 mas vergas", "elo", "§a%top%# %player% %rank% §c%elo% ELO"), false);
            }
        }
        if (Principal.getPlugin().getConfig().getConfigurationSection("holograms.topwins") != null) {
            int topwins = 0;
            for (String s : Principal.getPlugin().getConfig().getConfigurationSection("holograms.topwins").getKeys(false)) {
                Hologram hologram = new Hologram("topwins" + topwins++, Utils.deserializeLoc(Principal.getPlugin().getConfig().getString("holograms.topwins." + s)), getLinesTop("&6Top 10 partidas ganadas", "wins", "§a%top%# %player% §c%wins% Partidas Ganadas alv"), false);
            }
        }
        if (Principal.getPlugin().getConfig().getConfigurationSection("holograms.topkills") != null) {
            int topkills = 0;
            for (String s : Principal.getPlugin().getConfig().getConfigurationSection("holograms.topkills").getKeys(false)) {
                Hologram hologram = new Hologram("topkills" + topkills++, Utils.deserializeLoc(Principal.getPlugin().getConfig().getString("holograms.topkills." + s)), getLinesTop("&6Top 10 kills", "kills", "§a%top%# %player% §c%kills% Kills"), false);
            }
        }

    }

    public static void removeHolograms() {
        for (Hologram hologram : holograms) {
            hologram.removeHologram();
        }
        for (Entity entity : Bukkit.getWorld("lobby").getEntities()) {
            if (entity.getType().equals(EntityType.ARMOR_STAND)) {
                entity.remove();
            }
        }
    }

    private static List<String> getLinesTop(String firstname, String toptype, String format) {
        List<String> lines = new ArrayList<>();
        lines.add(ChatColor.translateAlternateColorCodes('&', firstname));
        lines.add(" ");
        int id = 1;
        ResultSet resultSet = Principal.getPlugin().getHikariSQLManager().Query("SELECT * FROM stats ORDER BY " + toptype + " DESC Limit 10");
        try {
            while (resultSet.next()) {
                int elo = resultSet.getInt("elo");
                int wins = resultSet.getInt("wins");
                int played = resultSet.getInt("played");
                int kills = resultSet.getInt("kills");
                String rank = PlayerManager.getRank(elo);
                String playername = resultSet.getString("realname");
                lines.add(format.replace("%elo%", elo + "").replace("%rank%", rank).replace("%player%", playername).replace("%top%", (id++) + "").replace("%wins%", wins + "").replace("%played%", played + "").replace("%kills%", kills + ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

}
