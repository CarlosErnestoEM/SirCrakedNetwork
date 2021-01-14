package com.pollogamer.tntrun.manager;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.tntrun.Principal;
import com.pollogamer.tntrun.extras.Lang;
import com.pollogamer.tntrun.extras.ScoreboardAPI;
import com.pollogamer.tntrun.extras.Utils;
import com.pollogamer.tntrun.listener.PlayerListener;
import com.pollogamer.tntrun.task.GameTask;
import com.pollogamer.tntrun.task.LobbyTask;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.DataOutputStream;

public class PlayerManager {

    public static void setScoreboardWaiting(Player p) {
        ScoreboardAPI score = new ScoreboardAPI("&e&lTNT TNT Games");
        score.add(" ", 11);
        score.add("Mapa: &a" + Principal.plugin.getConfig().getString("mapa"), 10);
        score.add("Jugadores:&a " + PlayerListener.players.size() + "/" + Bukkit.getMaxPlayers(), 9);
        score.add("  ", 8);
        score.add("Esperando...", 7);
        score.add("   ", 6);
        score.add("Juego: &aTNT Run", 5);
        score.add("    ", 4);
        score.add("ID: &a" + Bukkit.getServerName(), 3);
        score.add("     ", 2);
        score.add("&eplay.sircraked.com", 1);
        score.update();
        score.send(p);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                }
                if (Lang.started) {
                    this.cancel();
                    return;
                }
                if (Lang.starting) {
                    score.add("Comenzando en §a" + LobbyTask.starting + "s", 7);
                }
                score.add("Jugadores:&a " + PlayerListener.players.size() + "/" + Bukkit.getMaxPlayers(), 9);
                score.update();
                score.send(p);
            }
        }.runTaskTimer(Principal.plugin, 0, 20);
    }

    public static void setScoreboardGame(Player p) {
        ScoreboardAPI score = new ScoreboardAPI("&e&lTNT Run");
        score.add(" ", 12);
        score.add("Dobles Saltos: &a" + PlayerListener.doublejump.get(p) + "/1", 11);
        score.add("  ", 10);
        score.add("Tiempo: &a" + Utils.serializetime(GameTask.time), 9);
        score.add("   ", 8);
        score.add("Arriba de ti: &a0", 7);
        score.add("Abajo tuyo: &a0", 6);
        score.add("    ", 5);
        score.add("Jugadores:&a " + PlayerListener.players.size(), 4);
        score.add("     ", 2);
        score.add("&eplay.sircraked.com", 1);
        score.update();
        score.send(p);
        new BukkitRunnable() {
            @Override
            public void run() {
                score.add("Dobles Saltos: &a" + PlayerListener.doublejump.get(p) + "/1", 11);
                score.add("Tiempo: &a" + Utils.serializetime(GameTask.time), 9);
                score.add("Jugadores:&a " + PlayerListener.players.size(), 4);
                score.add("Coins: &a" + Utils.getCoins(p), 3);
                score.update();
                score.send(p);
            }
        }.runTaskTimer(Principal.plugin, 0, 21);
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
        player.sendPluginMessage(Principal.plugin, "BungeeCord", b.toByteArray());
    }


    public static void setCleanPlayer(Player p) {
        p.setLevel(0);
        p.setGameMode(GameMode.ADVENTURE);
        p.getInventory().clear();
        p.setFoodLevel(20);
        p.removePotionEffect(PotionEffectType.SPEED);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
    }

    public static void setSpectator(Player p) {
        if (PlayerListener.players.contains(p)) {
            PlayerListener.players.remove(p);
        }
        if (!PlayerListener.spectators.contains(p)) {
            PlayerListener.spectators.add(p);
            for (Player p2 : Bukkit.getOnlinePlayers()) {
                p2.hidePlayer(p);
            }
            p.setGameMode(GameMode.ADVENTURE);
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 10000, 0));
            p.setAllowFlight(true);
            p.setFlying(true);
            p.teleport(Lang.spawn);
            p.getInventory().clear();
            p.getInventory().setItem(8, new ItemStackBuilder(Material.BED).setName("§c§lRegresar al Lobby §7(Click Derecho)"));
            p.getInventory().setItem(0, new ItemStackBuilder(Material.COMPASS).setName("§cEspectear"));
            p.sendMessage("§eAhora eres un espectador!");
        }
    }

}
