package com.pollogamer.sircrakedserver.coins;

import com.pollogamer.SirCrakedCore;
import org.bukkit.entity.Player;

import java.sql.ResultSet;

public class CoinsAPI {

    public static int getCoins(Player p) {
        int c = 0;
        try {
            ResultSet rs = SirCrakedCore.getCore().getHikariSQLManager().Query("SELECT `coins` FROM `players_coins` WHERE `player_uuid`='" + p.getUniqueId() + "'");
            while (rs.next()) {
                c = rs.getInt(1);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return c;
    }

    public static void addCoins(Player p, int coins) {
        int c = getCoins(p);
        c += coins;
        SirCrakedCore.getCore().getHikariSQLManager().Update("UPDATE `players_coins` SET `coins`='" + c + "' WHERE `player_uuid`='" + p.getUniqueId() + "'");
    }

    public static void removeCoins(Player p, int coins) {
        int c = getCoins(p);
        c -= coins;
        SirCrakedCore.getCore().getHikariSQLManager().Update("UPDATE `players_coins` SET `coins`='" + c + "' WHERE `player_uuid`='" + p.getUniqueId() + "'");
    }

    public static void setCoins(Player p, int coins) {
        SirCrakedCore.getCore().getHikariSQLManager().Update("UPDATE `players_coins` SET `coins`='" + coins + "' WHERE `player_uuid`='" + p.getUniqueId() + "'");
    }
}
