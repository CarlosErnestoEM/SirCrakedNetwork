package com.pollogamer.tnttag.task;

import com.pollogamer.tnttag.Principal;
import com.pollogamer.tnttag.extras.Utils;
import com.pollogamer.tnttag.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EndingTask extends BukkitRunnable {

    int stop = 10;

    public EndingTask() {
        runTaskTimer(Principal.plugin, 40, 20);
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
                PlayerManager.sendToServer(p, "lobby1");
            }
        } else if (stop == 0) {
            Principal.setupGame();
            cancel();
            return;
        }
    }
}
