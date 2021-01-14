package com.pollogamer.tnttag.manager;

import com.minebone.itemstack.ItemStackBuilder;
import com.nametagedit.plugin.NametagEdit;
import com.pollogamer.tnttag.Principal;
import com.pollogamer.tnttag.extras.Lang;
import com.pollogamer.tnttag.extras.Scoreboard;
import com.pollogamer.tnttag.extras.Utils;
import com.pollogamer.tnttag.listener.PlayerListener;
import com.pollogamer.tnttag.task.GameTask;
import com.pollogamer.tnttag.task.LobbyTask;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.DataOutputStream;
import java.util.Random;


public class PlayerManager {

    public static Random r = new Random();

    public static void cleanPlayer(Player p) {
        p.setLevel(0);
        p.setGameMode(GameMode.ADVENTURE);
        p.setFireTicks(0);
        p.setFoodLevel(20);
        p.setHealth(p.getMaxHealth());
    }

    public void setScoreboardLobbyWaiting(Player p) {
        Scoreboard score = new Scoreboard("&e&LTHE TNT GAMES");
        score.add(" ", 11);
        score.add("Mapa: §a" + Principal.plugin.getConfig().getString("mapa"), 10);
        score.add("Jugadores:§a " + PlayerListener.players.size() + "/" + Bukkit.getMaxPlayers(), 9);
        score.add("  ", 8);
        score.add("Esperando...", 7);
        score.add("   ", 6);
        score.add("Juego: §ATNT Tag", 5);
        score.add("    ", 4);
        score.add("ID: §a" + Bukkit.getServerName(), 3);
        score.add("     ", 2);
        score.add("&eplay.latingamers.mx", 1);
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
                    p.setLevel(LobbyTask.starting + 1);
                }
                score.add("Jugadores:§a " + PlayerListener.players.size() + "/" + Bukkit.getMaxPlayers(), 9);
                score.update();
                score.send(p);
            }
        }.runTaskTimer(Principal.plugin, 0, 20);
    }

    public static void launchFirework(Player p) {
        Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder().withColor(Color.GREEN).with(FireworkEffect.Type.CREEPER).build();
        FireworkEffect effect2 = FireworkEffect.builder().withColor(Color.RED).with(FireworkEffect.Type.CREEPER).build();
        int random = r.nextInt(2);
        if (random == 1) {
            fwm.addEffect(effect);
        } else {
            fwm.addEffect(effect2);
        }
        fw.setFireworkMeta(fwm);
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

    public void setScoreboardInGame(Player p) {
        Scoreboard score = new Scoreboard("&e&LTNT TAG");
        score.add("   ", 8);
        score.add("Tiempo: §a" + GameTask.game + "s", 7);
        score.add("Ronda: §a" + GameTask.fase, 6);
        score.add(" ", 5);
        score.add("Jugadores: §a" + PlayerListener.players.size(), 4);
        score.add("Coins: §a" + Utils.getCoins(p), 3);
        score.add("  ", 2);
        score.add("&eplay.latingamers.mx", 1);
        score.update();
        score.send(p);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                }
                if (GameTask.game <= 30 && GameTask.game >= 11) {
                    score.add("Tiempo: §6" + GameTask.game + "s", 7);
                } else if (GameTask.game >= 0 && GameTask.game <= 10) {
                    score.add("Tiempo: §c" + GameTask.game + "s", 7);
                } else {
                    score.add("Tiempo: §a" + GameTask.game + "s", 7);
                }
                score.add("Ronda: §a" + GameTask.fase, 6);
                score.add("Jugadores: §a" + PlayerListener.players.size(), 4);
                score.add("Coins: §a" + Utils.getCoins(p), 3);
                score.update();
                score.send(p);
            }
        }.runTaskTimer(Principal.plugin, 0, 21);
    }

    public static void setSpectator(Player p) {
        cleanPlayer(p);
        NametagEdit.getApi().setPrefix(p, "§7[Spec] §a");
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
        p.getInventory().setHelmet(null);
        if (!PlayerListener.spectators.contains(p)) {
            PlayerListener.spectators.add(p);
        }
    }

}
