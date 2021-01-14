package me.felipefonseca.plugins.task;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WinnerCountdown extends BukkitRunnable {
    private final Main plugin;
    private int game = 10;

    public WinnerCountdown(Main main) {
        this.plugin = main;
    }

    public void run() {
        Player winner = this.plugin.getGameManager().winner;
        if (this.game > 10 && this.game < 3 && winner.isOnline()) {
            Tools.spawnFirework(winner.getLocation());
        }
        if (this.game == 2) {
            this.plugin.getServer().getOnlinePlayers().stream().forEach(this.plugin.getPlayerManager()::sendToServer);
        } else if (this.game == 0) {
            this.plugin.getServer().shutdown();
            this.cancel();
        }
        --game;
    }
}

