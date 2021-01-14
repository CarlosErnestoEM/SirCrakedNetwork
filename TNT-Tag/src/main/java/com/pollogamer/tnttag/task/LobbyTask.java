package com.pollogamer.tnttag.task;

import com.pollogamer.tnttag.Principal;
import com.pollogamer.tnttag.extras.Lang;
import com.pollogamer.tnttag.listener.PlayerListener;
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
        if ((starting >= 1 && starting <= 5) || starting == 10) {
            Bukkit.broadcastMessage("§eEl juego empezara en §c" + starting + "§e " + (starting != 1 ? "segundos" : "segundo") + "!");
        } else if (starting == 0) {
            for (Player p : PlayerListener.players) {
                if (p != null) {
                    p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    Principal.playerManager.setScoreboardInGame(p);
                }
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.getInventory().clear();
            }
            new GameTask();
            cancel();
        }
        --starting;
    }
}
