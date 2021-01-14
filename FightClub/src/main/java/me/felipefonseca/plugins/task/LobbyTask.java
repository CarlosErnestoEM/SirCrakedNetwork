/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package me.felipefonseca.plugins.task;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.manager.enums.GameState;
import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTask
        extends BukkitRunnable {
    private final Main plugin;
    private int count = 20;

    public LobbyTask(Main plugin) {
        this.plugin = plugin;
    }

    public void run() {
        this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                    players.setLevel(this.count);
                }
        );
        this.plugin.getPlayerManager().getBoard().setTitle("\u00a74Fight\u00a7cClub \u00a76" + Tools.transform(this.count));
        if (this.count == 20) {
            this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                        this.plugin.getMessagesController().sendTitle(players, "&4&lFight&c&lClub", "", 5, 10, 5);
                    }
            );
            this.plugin.getMessagesController().sendBroadcast("&a&l20 segundos &7para iniciar.");
        } else if (this.count > 0 && this.count <= 5) {
            this.plugin.getGameManager().getPlayers().stream().map(players -> {
                        this.plugin.getMessagesController().sendTitle(players, "&c&l" + this.count, "", 5, 10, 5);
                        return players;
                    }
            ).map(players -> {
                        players.playSound(players.getLocation(), Sound.BURP, 1.0f, 1.0f);
                        return players;
                    }
            ).map(players -> {
                        players.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 1));
                        return players;
                    }
            ).forEach(players -> {
                        players.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 140, 1));
                    }
            );
            this.plugin.getMessagesController().sendBroadcast("&7El juego iniciar\u00e1 en &c&l" + this.count);
        } else if (this.count == 0) {
            if (this.plugin.getGameManager().getPlayers().size() == 16 || this.plugin.getGameManager().getPlayers().size() == 8 || this.plugin.getGameManager().getPlayers().size() == 24 || this.plugin.getGameManager().getPlayers().size() == 32) {
                for (Player players2 : this.plugin.getGameManager().getPlayers()) {
                    this.plugin.getArenaManager().getNextSpawnPoint(players2);
                    this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
                                this.plugin.getSkillManager().CheckKit(players2);
                                this.plugin.getMessagesController().sendTitle(players2, "&b&lMucha suerte!", "", 5, 10, 5);
                                players2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 127));
                            }
                    );
                }
                new GameTask(this.plugin, 1).runTaskTimer(this.plugin, 20, 20);
                GameState.state = GameState.IN_GAME;
                this.plugin.getSkillManager().checkRegeneracion();
                this.plugin.getSkillManager().checkVida();
                this.cancel();
            } else {
                this.count = 20;
                plugin.getMessagesController().sendBroadcast("&eNo son suficientes jugadores para empezar :/");
                this.plugin.getGameManager().setStarted(false);
                this.cancel();
                return;
            }
        }
        --this.count;
    }
}

