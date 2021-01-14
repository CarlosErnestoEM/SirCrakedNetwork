package com.pollogamer.uhcsimulator.manager;

import com.pollogamer.sircrakedserver.utils.ScoreboardAPI;
import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.extras.Utils;
import com.pollogamer.uhcsimulator.objects.UHCPlayer;
import com.pollogamer.uhcsimulator.task.GameTask;
import com.pollogamer.uhcsimulator.task.LobbyTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class PlayerManager {

    public static void setCleanPlayer(Player p) {
        p.setLevel(0);
        p.setFoodLevel(20);
        p.setHealth(p.getMaxHealth());
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setExp(0);
        p.setFireTicks(0);
        p.getActivePotionEffects().stream().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
    }

    public static void setGameScoreboard(Player player) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);
        ScoreboardAPI score = new ScoreboardAPI("§e§LUHCSimulator", true, true);
        score.add("", 14);
        score.add("§aAsesinatos: §f" + Principal.getPlugin().getKillManager().getKills(player), 13);
        score.add("§aJugadores: §f" + Lang.players.size(), 12);
        score.add("§aEspectadores: §f" + Lang.spectators.size(), 11);
        score.add(" ", 10);
        score.add("§aTiempo del Borde:", 9);
        score.add(" §e» §f" + Utils.serializeTime(GameTask.time), 8);
        score.add("  ", 7);
        score.add("§aBorde:", 6);
        score.add(" §e» §f" + Lang.bordersize + " x " + Lang.bordersize, 5);
        score.add("   ", 4);
        score.add("§aRango:", 3);
        score.add(" §e» §f" + getRank(uhcPlayer.getElo()), 2);
        score.add("    ", 1);
        score.add("§eplay.sircraked.com", 0);
        score.update();
        score.send(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                } else {
                    score.add("§aAsesinatos: §f" + Principal.getPlugin().getKillManager().getKills(player), 13);
                    score.add("§aJugadores: §f" + Lang.players.size(), 12);
                    score.add("§aEspectadores: §f" + Lang.spectators.size(), 11);
                    score.add(" §e» §f" + Lang.bordersize + " x " + Lang.bordersize, 5);
                    score.add(" §e» §f" + Utils.serializeTime(GameTask.time), 8);
                    score.update();
                    score.send(player);
                }
            }
        }.runTaskTimer(Principal.getPlugin(), 20, 20);
    }

    public static void sendToServer(Player player, String targetServer) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(targetServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(Principal.getPlugin(), "BungeeCord", b.toByteArray());
    }

    public static void setLobbyScoreboard(Player player) {
        ScoreboardAPI score = new ScoreboardAPI("§e§lUHCSimulator", true, true);
        score.add("    ", 9);
        score.add("§e" + player.getName(), 8);
        score.add("  ", 7);
        score.add("Jugadores: §a" + Lang.players.size() + "/" + Bukkit.getMaxPlayers(), 6);
        score.add(" ", 5);
        score.add((Lang.starting ? "Empezando en §a" + (LobbyTask.count == 1 ? " segundo!" : " segundos!") : "Esperando..."), 4);
        score.add("   ", 3);
        score.add("ID: §a" + Bukkit.getServerName(), 2);
        score.add("", 1);
        score.add("§eplay.sircraked.com", 0);
        score.update();
        score.send(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || Lang.started) {
                    cancel();
                    return;
                } else {
                    score.add("Jugadores: §a" + Lang.players.size() + "/" + Bukkit.getMaxPlayers(), 6);
                    score.add((Lang.starting ? "Empezando en §a" + LobbyTask.count + "s" : "Esperando..."), 4);
                    score.update();
                    score.send(player);
                }
            }
        }.runTaskTimer(Principal.getPlugin(), 10, 10);
    }

    public static String getRank(int elo) {
        if (elo >= 500) {
            if (elo >= 500 && elo <= 1000) {
                return "§6Bronze V";
            } else if (elo >= 1000 && elo < 1200) {
                return "§6Bronze IV";
            } else if (elo >= 1200 && elo < 1400) {
                return "§6Bronze III";
            } else if (elo >= 1400 && elo < 1600) {
                return "§6Bronze II";
            } else if (elo >= 1600 && elo < 2000) {
                return "§6Bronze I";
            } else if (elo >= 2000 && elo < 2200) {
                return "§7Silver V";
            } else if (elo >= 2200 && elo < 2400) {
                return "§7Silver IV";
            } else if (elo >= 2400 && elo < 2600) {
                return "§7Silver III";
            } else if (elo >= 2600 && elo < 2800) {
                return "§7Silver II";
            } else if (elo >= 2800 && elo < 3000) {
                return "§7Silver I";
            } else if (elo >= 3000 && elo < 3200) {
                return "§eGold V";
            } else if (elo >= 3200 && elo < 3400) {
                return "§eGold IV";
            } else if (elo >= 3400 && elo < 3600) {
                return "§eGold III";
            } else if (elo >= 3600 && elo < 3800) {
                return "§eGold II";
            } else if (elo >= 3800 && elo < 4000) {
                return "§eGold I";
            } else if (elo >= 4000 && elo < 4200) {
                return "§aEmerald V";
            } else if (elo >= 4200 && elo < 4400) {
                return "§aEmerald IV";
            } else if (elo >= 4400 && elo < 4600) {
                return "§aEmerald III";
            } else if (elo >= 4600 && elo < 4800) {
                return "§aEmerald II";
            } else if (elo >= 4800 && elo < 5000) {
                return "§aEmerald I";
            } else if (elo >= 5000 && elo < 5200) {
                return "§bDiamond V";
            } else if (elo >= 5200 && elo < 5400) {
                return "§bDiamond IV";
            } else if (elo >= 5400 && elo < 5600) {
                return "§bDiamond III";
            } else if (elo >= 5600 && elo < 5800) {
                return "§bDiamond II";
            } else if (elo >= 5800 && elo < 6000) {
                return "§bDiamond I";
            } else if (elo >= 6000) {
                return "§5Master";
            }
        }
        return "Unranked";
    }
}
