/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.block.BlockState
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
import me.felipefonseca.plugins.manager.enums.FaseState;
import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask
        extends BukkitRunnable {
    private final Main plugin;
    private final int fase;
    public static int fasee;
    private int count;

    public GameTask(Main plugin, int fase) {
        this.plugin = plugin;
        this.fase = fase;
        fasee = 1;
    }

    public void run() {
        if (this.plugin.getGameManager().isInDeathMatch() || this.plugin.getGameManager().isEnding()) {
            this.cancel();
        }
        this.plugin.getPlayerManager().getBoard().setTitle("\u00a74Fight\u00a7cClub \u00a76" + Tools.transform(this.count));
        switch (this.count) {
            case 0: {
                this.plugin.getMessagesController().sendBroadcast("&7Fase &e&l" + this.fasee);
                switch (this.fase) {
                    case 1: {
                        FaseState.fase = FaseState.FASE_1;
                        break;
                    }
                    case 2: {
                        FaseState.fase = FaseState.FASE_2;
                        break;
                    }
                    case 3: {
                        FaseState.fase = FaseState.FASE_3;
                    }
                    case 4:
                        if (Bukkit.getMaxPlayers() == 32) {
                            FaseState.fase = FaseState.FASE_4;
                        }
                }
                this.plugin.getMessagesController().sendBroadcast("&7La Fase durar\u00e1 2 minutos, \u00a1apres\u00farate!.");
                break;
            }
            case 60: {
                this.plugin.getMessagesController().sendBroadcast("&7S\u00f3lo queda &a&l1 minuto&7.");
                break;
            }
            case 70: {
                this.plugin.getGameManager().getPlayers().stream().filter(players -> !this.plugin.getGameManager().getPlayersWithKit().contains((Object) players)).map(players -> {
                            this.plugin.getGameManager().addPlayerWithKit(players);
                            return players;
                        }
                ).map(players -> {
                            this.plugin.getPlayerManager().addReward(players);
                            return players;
                        }
                ).forEach(players -> {
                            players.playSound(players.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                        }
                );
                break;
            }
            case 115: {
                this.plugin.getArenaManager().reloadSpawns();
                this.plugin.getArenaManager().setLastSpawnGiven(0);
                for (BlockState block : this.plugin.getArenaManager().getBlocksBreaked()) {
                    block.update(true);
                }
                this.plugin.getArenaManager().getBlocksBreaked().clear();
                this.plugin.getGameManager().getPlayers().stream().map(players -> {
                            players.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
                            return players;
                        }
                ).forEach(players -> {
                            players.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
                        }
                );
                break;
            }
            case 120: {
                this.plugin.getGameManager().getPlayersWithKit().clear();
                switch (this.fase) {
                    case 1: {
                        if (this.plugin.getGameManager().isDeathmatchStarted()) {
                            this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                                        this.plugin.getArenaManager().teleport(players);
                                    }
                            );
                            new DeathMatchTask(this.plugin).runTaskTimer((Plugin) this.plugin, 20, 20);
                            this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                                        this.plugin.getGameManager().getPlayers().stream().forEach(p -> {
                                                    p.playSound(p.getLocation(), Sound.ENDERDRAGON_DEATH, 1.0f, 1.0f);
                                                }
                                        );
                                    }
                            );
                            break;
                        }
                        if (this.plugin.getGameManager().isDeathmatchStarted()) break;
                        this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                                    this.plugin.getArenaManager().getNextSpawnPoint(players);
                                }
                        );
                        fasee++;
                        new GameTask(this.plugin, 2).runTaskTimer(this.plugin, 20, 20);
                        break;
                    }
                    case 2: {
                        if (this.plugin.getGameManager().isDeathmatchStarted()) {
                            this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                                        this.plugin.getArenaManager().teleport(players);
                                    }
                            );
                            new DeathMatchTask(this.plugin).runTaskTimer((Plugin) this.plugin, 20, 20);
                            this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                                        this.plugin.getGameManager().getPlayers().stream().forEach(p -> {
                                                    p.playSound(p.getLocation(), Sound.ENDERDRAGON_DEATH, 1.0f, 1.0f);
                                                }
                                        );
                                    }
                            );
                            break;
                        }
                        if (this.plugin.getGameManager().isDeathmatchStarted()) break;
                        this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                                    this.plugin.getArenaManager().getNextSpawnPoint(players);
                                }
                        );
                        fasee++;
                        new GameTask(this.plugin, 3).runTaskTimer(this.plugin, 20, 20);
                        break;
                    }
                    case 3: {
                        if (Bukkit.getMaxPlayers() == 32) {
                            if (this.plugin.getGameManager().isDeathmatchStarted()) {
                                this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                                            this.plugin.getArenaManager().teleport(players);
                                        }
                                );
                                new DeathMatchTask(this.plugin).runTaskTimer((Plugin) this.plugin, 20, 20);
                                this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                                            this.plugin.getGameManager().getPlayers().stream().forEach(p -> {
                                                        p.playSound(p.getLocation(), Sound.ENDERDRAGON_DEATH, 1.0f, 1.0f);
                                                    }
                                            );
                                        }
                                );
                                break;
                            }
                            if (this.plugin.getGameManager().isDeathmatchStarted()) break;
                            this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                                        this.plugin.getArenaManager().getNextSpawnPoint(players);
                                    }
                            );
                            fasee++;
                            new GameTask(this.plugin, 3).runTaskTimer(this.plugin, 20, 20);
                            break;
                        } else {
                            this.plugin.getGameManager().checkTask();
                        }
                    }
                    case 4: {
                        if (Bukkit.getMaxPlayers() == 32) {
                            this.plugin.getGameManager().checkTask();
                        }
                    }
                }
                this.cancel();
            }
        }
        ++this.count;
    }
}

