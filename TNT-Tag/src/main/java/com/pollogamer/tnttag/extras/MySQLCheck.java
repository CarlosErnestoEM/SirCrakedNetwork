package com.pollogamer.tnttag.extras;

import org.bukkit.entity.Player;

public class MySQLCheck {

    boolean exist = false;

    public MySQLCheck(Player p) {
        check(p);
    }

    public void check(Player p) {
       /* new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    ResultSet rs = Principal.mySQLManager.Query("SELECT `coins` FROM `tntgames` WHERE `jugador`='" + p.getName().toLowerCase() + "'");
                    while (rs.next()) {
                        exist = true;
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }
                if (!exist) {
                    Principal.mySQLManager.Update("INSERT INTO `tntgames` (`jugador`, `coins`) VALUES ('" + p.getName().toLowerCase() + "', '" + 0 + "')");
                }
            }
        }.runTaskAsynchronously(Principal.plugin);*/
    }
}

