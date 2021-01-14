package com.pollogamer.uhcsimulator.task;

import com.pollogamer.sircrakedserver.utils.MessagesController;
import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.manager.MySQLManager;
import com.pollogamer.uhcsimulator.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WinnerTask extends BukkitRunnable {

    public static Player winner;
    private int time = 15;

    public WinnerTask() {
        winner = Lang.players.get(0);
        Principal.getPlugin().getEloManager().addElo(winner, 15);
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§6§LUHCSimulator §7» §aEl ganador es " + winner.getName());
        Bukkit.broadcastMessage("");
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == winner) {
                MessagesController.sendTitle(player, "&a&lVICTORIA!", "&cHas ganado alv", 10, 20, 10);
            } else {
                MessagesController.sendTitle(player, "&b&lPARTIDA TERMINADA!", "El ganador es " + winner.getName(), 10, 20, 10);
            }
        }
        MySQLManager.update(winner, "wins", 1);
        MySQLManager.uploadToMySQL(winner);
        runTaskTimer(Principal.getPlugin(), 0, 20);
    }

    @Override
    public void run() {
        if (time > 0) {
            //spawn particles
            --time;
        }
        if (time == 3) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerManager.sendToServer(player, "HUB-1");
            }
            //change motd
        }
        if (time == 0) {
            Bukkit.shutdown();
        }
    }
}
