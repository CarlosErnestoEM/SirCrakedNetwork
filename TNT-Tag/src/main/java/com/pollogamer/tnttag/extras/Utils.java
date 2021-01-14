package com.pollogamer.tnttag.extras;

import com.pollogamer.tnttag.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Utils {

    public static String serializeLoc(Location l) {
        return l.getWorld().getName() + "," + l.getX() + "," + l.getY() + "," + l.getZ() + "," + l.getYaw() + "," + l.getPitch();
    }

    public static Location deserializeLoc(String s) {
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Double.parseDouble(st[1]), Double.parseDouble(st[2]), Double.parseDouble(st[3]), Float.parseFloat(st[4]), Float.parseFloat(st[5]));
    }

    public static int getCoins(Player p) {
        if (PlayerListener.coins.containsKey(p)) {
            return PlayerListener.coins.get(p);
        }
        return 0;
    }

    public static void addCoins(Player p, int coins) {
        if (PlayerListener.coins.containsKey(p)) {
            int oldcoins = PlayerListener.coins.get(p);
            PlayerListener.coins.put(p, oldcoins + coins);
        } else {
            PlayerListener.coins.put(p, coins);
        }
        p.sendMessage("§6+" + coins + " coins!");
        MessagesController.sendActionBar(p, "§6+" + coins + " coins!");
    }

    public static void coinsToMySQL(Player p) {
        if (PlayerListener.coins.containsKey(p)) {
            int coins = PlayerListener.coins.get(p);
            addCoinsMySQL(p, coins);
        }
    }

    public static int getCoinsMySQL(Player p) {
        int c = 0;
        /*try {
            ResultSet rs = Principal.mySQLManager.Query("SELECT `coins` FROM `tntgames` WHERE `jugador`='" + p.getName().toLowerCase() + "'");
            while (rs.next()) {
                c = rs.getInt(1);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }*/
        return c;
    }

    public static void addCoinsMySQL(Player p, int coins) {
        int c = getCoinsMySQL(p);
        c += coins;
        // Principal.mySQLManager.Update("UPDATE `tntgames` SET `coins`='" + c + "' WHERE `jugador`='" + p.getName().toLowerCase() + "'");
    }
}
