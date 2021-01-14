package com.pollogamer.tntrun.task;

import com.pollogamer.tntrun.Principal;
import com.pollogamer.tntrun.extras.Lang;
import com.pollogamer.tntrun.extras.Utils;
import com.pollogamer.tntrun.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    public static int time;
    public static String winner;

    public GameTask() {
        time = 0;
        Lang.starting = false;
        runTaskTimer(Principal.plugin, 10, 20);
        Lang.started = true;
        for (Player p : PlayerListener.players) {
            p.teleport(Lang.spawn);
        }
        Bukkit.broadcastMessage("§eEl juego ha comenzado!");
    }

    @Override
    public void run() {
        ++time;
        if (time == 4) {
            Lang.tnt = true;
        }
        if (time % 15 == 0) {
            for (Player p : PlayerListener.players) {
                Utils.addCoins(p, 4);
            }
        }
        if (PlayerListener.players.size() == 1) {
            winner = PlayerListener.players.get(0).getName();
            Lang.tnt = false;
            this.cancel();
            return;
        }
    }
}
