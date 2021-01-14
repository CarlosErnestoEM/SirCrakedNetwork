package me.felipefonseca.plugins.task;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.manager.GameState;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportCountdown extends BukkitRunnable {
    private final Main plugin;
    private int count = 5;

    public TeleportCountdown(Main main) {
        this.plugin = main;
    }

    public void run() {
        if (this.count >= 1 && count <= 5) {
            this.plugin.getMessageSender().sendBroadcast("&7Seras liberado en " + count);
        }
        if (this.count == 5) {
            this.plugin.getGameManager().getPlayersInGame().stream().forEach(player -> {
                        this.plugin.getArenaManager().teleport(player);
                        this.plugin.getPlayerManager().loadKit(player);
                    }
            );
            this.plugin.getArenaManager().fillChest();
        } else if (this.count == 0) {
            this.plugin.getGameManager().getPlayersInGame().stream().forEach(player -> {
                        this.plugin.getArenaManager().fixPlayer(player.getLocation());
                    }
            );
            GameState.State.state = null;
            new GameCountdown(this.plugin).runTaskTimer(this.plugin, 0, 20);
            this.cancel();
        }
        --this.count;
    }
}

