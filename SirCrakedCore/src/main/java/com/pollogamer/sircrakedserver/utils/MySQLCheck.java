package com.pollogamer.sircrakedserver.utils;

import com.pollogamer.SirCrakedCore;
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
                    ResultSet rs = SirCrakedCore.getCore().getHikariSQLManager().Query("SELECT `ID` FROM `usuarios` WHERE `Username`='" + p.getName().toLowerCase() + "'");
                    while (rs.next()) {
                        exist = true;
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }
                if (!exist) {
                    SirCrakedCore.getCore().getHikariSQLManager().Update("INSERT INTO `usuarios` (`Realname`, `Username`, `firstJoin`, `coins`, `level`) VALUES ('" + p.getName() + "','" + p.getName().toLowerCase() + "','" + p.getFirstPlayed() + "','" + 20 + "','" + 0.0 + "')");
                }
                cancel();
            }
        }.runTaskAsynchronously(SirCrakedCore.getCore());
    }

}
