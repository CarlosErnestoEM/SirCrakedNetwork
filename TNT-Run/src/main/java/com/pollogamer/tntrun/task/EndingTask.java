package com.pollogamer.tntrun.task;

import com.pollogamer.tntrun.Principal;
import com.pollogamer.tntrun.extras.Utils;
import com.pollogamer.tntrun.listener.PlayerListener;
import com.pollogamer.tntrun.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EndingTask extends BukkitRunnable {

    int stop = 10;

    public EndingTask() {
        runTaskTimer(Principal.plugin, 40, 20);
        GameTask.winner = PlayerListener.players.get(0).getName();
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§cEl ganador es " + GameTask.winner);
        Bukkit.broadcastMessage("");
        Player p = Bukkit.getPlayer(GameTask.winner);
        if (p != null) {
            Utils.addCoins(p, 20);
        }
    }

    @Override
    public void run() {
        --stop;
        if (stop == 3) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerManager.sendToServer(p, "Hub-1");
            }
        } else if (stop <= 0) {
            Bukkit.shutdown();
        }
    }
}
