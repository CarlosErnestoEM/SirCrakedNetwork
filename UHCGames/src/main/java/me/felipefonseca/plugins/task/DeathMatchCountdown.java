package me.felipefonseca.plugins.task;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.manager.GameState;
import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathMatchCountdown extends BukkitRunnable {
    private final Main plugin;

    public DeathMatchCountdown(Main main) {
        this.plugin = main;
    }

    public void run() {
        if (GameState.state == GameState.ENDING) {
            this.cancel();
        }
        this.plugin.getGameManager().getPlayersInGame().stream().forEach(player -> {
                    this.plugin.getMessageSender().sendActionBar(player, "&f&lDeathMatch: &a&l" + Tools.transform(this.plugin.getArenaManager().deathMatchTime));
                }
        );
        if (this.plugin.getArenaManager().deathMatchTime == this.plugin.getConfig().getInt("deathMatchTime")) {
            this.plugin.getGameManager().getPlayersInGame().stream().forEach(player -> {
                        this.plugin.getArenaManager().teleport(player);
                    }
            );
        } else if (this.plugin.getArenaManager().deathMatchTime == 0) {
            this.plugin.getGameManager().getPlayersInGame().stream().forEach(player -> {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, 1));
                    }
            );
        }
        --this.plugin.getArenaManager().deathMatchTime;
    }
}

