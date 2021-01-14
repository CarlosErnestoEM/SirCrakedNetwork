/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.felipefonseca.plugins.task;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathMatchTask
        extends BukkitRunnable {
    private final Main plugin;
    private int count = 300;

    public DeathMatchTask(Main plugin) {
        this.plugin = plugin;
    }

    public void run() {
        if (this.plugin.getGameManager().isDeathmatchStarted()) {
            this.cancel();
        }
        this.plugin.getPlayerManager().getBoard().setTitle("\u00a74Fight\u00a7cClub \u00a76" + Tools.transform(this.count));
        if (this.plugin.getGameManager().isEnding()) {
            this.cancel();
        }
        if (this.count != 300 && this.count == 0) {
            this.cancel();
        }
        --this.count;
    }
}

