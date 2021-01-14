package com.pollogamer.uhcsimulator.manager;

import com.pollogamer.uhcsimulator.Principal;
import org.bukkit.entity.Player;

import java.sql.ResultSet;

public class MySQLManager {

    public static int getData(Player p, String date) {
        int c = 0;
        try {
            ResultSet rs = Principal.getPlugin().getHikariSQLManager().Query("SELECT `" + date + "` FROM `stats` WHERE `player`='" + p.getName().toLowerCase() + "'");
            while (rs.next()) {
                c = rs.getInt(1);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return c;
    }

    public static void setData(Player p, String date, int i) {
        Principal.getPlugin().getHikariSQLManager().Update("UPDATE `stats` SET `" + date + "`='" + i + "' WHERE `player`='" + p.getName().toLowerCase() + "'");
    }

    public static void update(Player player, String date, int i) {
        int data = getData(player, date);
        setData(player, date, (data + i));
    }

    public static void uploadToMySQL(Player player) {
        if (Principal.getPlugin().getKillManager().getKills(player) > 0) {
            update(player, "kills", Principal.getPlugin().getKillManager().getKills(player));
        }
        if (Principal.getPlugin().getEloManager().getElo(player) != 0) {
            update(player, "elo", Principal.getPlugin().getEloManager().getElo(player));
        }
        update(player, "played", 1);
    }
}
