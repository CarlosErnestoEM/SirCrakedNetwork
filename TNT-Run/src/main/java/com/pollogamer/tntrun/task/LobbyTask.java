package com.pollogamer.tntrun.task;

import com.pollogamer.tntrun.Principal;
import com.pollogamer.tntrun.extras.Lang;
import com.pollogamer.tntrun.listener.PlayerListener;
import com.pollogamer.tntrun.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTask extends BukkitRunnable {

    public LobbyTask() {
        starting = 10;
        Lang.starting = true;
        runTaskTimer(Principal.plugin, 0, 20);
    }

    public static int starting;

    @Override
    public void run() {
        if (PlayerListener.players.size() < Lang.minplayers) {
            if (!Lang.forcestart) {
                Bukkit.broadcastMessage("§eInicio de juego cancelado porque no son suficientes jugadores :(");
                Lang.starting = false;
                this.cancel();
                return;
            }
        }
        for (Player p : PlayerListener.players) {
            p.setLevel(starting);
        }
        if ((starting >= 1 && starting <= 5) || starting == 10) {
            Bukkit.broadcastMessage("§eEl juego empezara en §c" + starting + "§e " + (starting != 1 ? "segundos" : "segundo") + "!");
        } else if (starting == 0) {
            for (Player p : PlayerListener.players) {
                if (p != null) {
                    PlayerManager.setScoreboardGame(p);
                }
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.getInventory().clear();
                p.setLevel(0);
            }
            new GameTask();
            cancel();
        }
        --starting;
    }
}
