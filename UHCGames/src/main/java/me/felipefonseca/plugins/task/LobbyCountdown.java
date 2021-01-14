package me.felipefonseca.plugins.task;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.manager.GameState;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyCountdown
        extends BukkitRunnable {
    private final Main plugin;
    private int count = 30;

    public LobbyCountdown(Main main) {
        this.plugin = main;
    }

    public void run() {
        this.plugin.getGameManager().getPlayersInGame().stream().forEach(player -> {
                    player.setLevel(count);
                }
        );
        if (count == 20 || count == 15 || count == 10) {
            this.plugin.getMessageSender().sendBroadcast("&7El juego empezara en " + count + " segundos");
        } else if (this.count > 0 && this.count <= 5) {
            this.plugin.getMessageSender().sendBroadcast("&7El juego empezara en " + count);
            this.plugin.getGameManager().getPlayersInGame().stream().map(player -> {
                        this.plugin.getMessageSender().sendTitle(player, "&c&l" + this.count, "", 40, 40, 40);
                        return player;
                    }
            ).forEach(player -> {
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                    }
            );
        } else if (this.count == 0) {
            GameState.state = GameState.IN_GAME;
            GameState.State.state = GameState.State.PPREGAME;
            new TeleportCountdown(this.plugin).runTaskTimer(this.plugin, 0, 20);
            this.cancel();
        }
        --this.count;
    }
}

