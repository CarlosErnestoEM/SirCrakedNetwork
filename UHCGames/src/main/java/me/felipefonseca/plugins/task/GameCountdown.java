package me.felipefonseca.plugins.task;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.manager.GameState;
import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCountdown
        extends BukkitRunnable {
    private final Main plugin;

    public GameCountdown(Main main) {
        this.plugin = main;
    }

    public void run() {
        this.plugin.getGameManager().getPlayersInGame().forEach(player -> {
                    this.plugin.getMessageSender().sendActionBar(player, "&a&l" + Tools.transform(this.plugin.getArenaManager().gameTime));
                }
        );
        if (GameState.state == GameState.DEATHMATCH || GameState.state == GameState.ENDING) {
            this.cancel();
        } else if (this.plugin.getArenaManager().gameTime == this.plugin.getConfig().getInt("gameTime")) {
            this.plugin.getMessageSender().sendBroadcast("§7El juego a empezado!");
        } else if (this.plugin.getArenaManager().gameTime == 0) {
            this.plugin.getGameManager().getPlayersInGame().forEach(player -> {
                        this.plugin.getMessageSender().sendTitle(player, "&c&lDeathMatch", "", 0, 5, 0);
                    }
            );
            this.plugin.getGameManager().dm = true;
            new DeathMatchCountdown(this.plugin).runTaskTimer(this.plugin, 0, 20);
            GameState.state = GameState.DEATHMATCH;
            GameState.State.state = GameState.State.NO_PVP;
            this.cancel();
        }
        --this.plugin.getArenaManager().gameTime;
    }
}

