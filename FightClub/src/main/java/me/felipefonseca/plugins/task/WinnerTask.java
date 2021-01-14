/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.FireworkEffect
 *  org.bukkit.FireworkEffect$Type
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.felipefonseca.plugins.task;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class WinnerTask
        extends BukkitRunnable {
    private final Main plugin;
    private int count = 10;

    public WinnerTask(Main plugin) {
        this.plugin = plugin;
    }

    public void run() {
        this.plugin.getGameManager().getPlayers().stream().forEach(winner -> {
                    Tools.firework(winner.getLocation(), Color.YELLOW, Color.RED, Color.BLUE, FireworkEffect.Type.CREEPER);
                }
        );
        this.plugin.getPlayerManager().getBoard().setTitle("\u00a74Fight\u00a7cClub \u00a76" + Tools.transform(this.count));
        switch (this.count) {
            case 7: {
                this.plugin.getPlayerManager().sendInfo();
                break;
            }
            case 2: {
                this.plugin.getServer().getOnlinePlayers().stream().forEach(players -> {
                            this.plugin.getPlayerManager().sendToServer(players);
                        }
                );
                break;
            }
            case 0: {
                this.plugin.getServer().shutdown();
                this.cancel();
            }
        }
        --this.count;
    }
}

