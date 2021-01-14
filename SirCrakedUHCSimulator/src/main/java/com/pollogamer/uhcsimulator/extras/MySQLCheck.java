package com.pollogamer.uhcsimulator.extras;

import com.pollogamer.uhcsimulator.Principal;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;

public class MySQLCheck {

    boolean exist = false;

    public MySQLCheck(Player p) {
        check(p);
    }

    public void check(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    ResultSet rs = Principal.getPlugin().getHikariSQLManager().Query("SELECT `realname` FROM `stats` WHERE `player`='" + p.getName().toLowerCase() + "'");
                    while (rs.next()) {
                        exist = true;
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }
                if (!exist) {
                    Principal.getPlugin().getHikariSQLManager().Update("INSERT INTO `stats` (`player`,`realname`,`wins`,`played`,`kills`,`elo`) VALUES ('" + p.getName().toLowerCase() + "', '" + p.getName() + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 1200 + "')");
                }
            }
        }.runTaskAsynchronously(Principal.getPlugin());
    }
}

