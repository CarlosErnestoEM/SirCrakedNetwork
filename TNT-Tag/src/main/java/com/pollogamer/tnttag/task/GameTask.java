package com.pollogamer.tnttag.task;

import com.nametagedit.plugin.NametagEdit;
import com.pollogamer.tnttag.Principal;
import com.pollogamer.tnttag.extras.Lang;
import com.pollogamer.tnttag.extras.MessagesController;
import com.pollogamer.tnttag.extras.Utils;
import com.pollogamer.tnttag.listener.PlayerListener;
import com.pollogamer.tnttag.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class GameTask extends BukkitRunnable {

    public static int game;
    public static int fase;
    public static String winner;

    public GameTask() {
        Lang.started = true;
        Lang.starting = false;
        if (PlayerListener.players.size() <= 1) {
            try {
                winner = PlayerListener.players.get(0).getName();
            } catch (Exception e) {
            }
            new EndingTask();
            return;
        }
        if (fase == 0) {
            for (Player p : PlayerListener.players) {
                p.teleport(Lang.spawn);
            }
        }
        for (Player p : PlayerListener.players) {
            if (PlayerListener.players.size() <= 5) {
                p.teleport(Lang.spawn);
            }
            p.removePotionEffect(PotionEffectType.SPEED);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10000, 1));
            p.setLevel(0);
        }
        game = 60;
        TNTRandom();
        ++fase;
        runTaskTimer(Principal.plugin, 0, 20);
    }

    @Override
    public void run() {
        --game;
        if (game == 0) {
            killPlayers();
            for (Player p : PlayerListener.players) {
                p.sendMessage("§eLo estas haciendo muy bien!, Ronda terminada");
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    new GameTask();
                }
            }.runTaskLater(Principal.plugin, 180);
            cancel();
            return;
        } else {
            for (Player p : PlayerListener.players) {
                p.setLevel(game + 1);
            }
        }
    }

    public void killPlayers() {
        PlayerListener.tntplayers.forEach(p -> {
            if (p == null) return;
            PlayerListener.tntplayers.remove(p);
            PlayerListener.players.remove(p);
            Bukkit.broadcastMessage("§7" + p.getName() + " §evalio verga!");
            p.getWorld().createExplosion(p.getLocation(), 0);
            PlayerManager.setSpectator(p);
        });
        for (Player p : PlayerListener.players) {
            Utils.addCoins(p, 8);
        }
    }

    public void TNTRandom() {
        Bukkit.broadcastMessage("§eLa TNT ha aparecido");
        double round = Math.ceil(PlayerListener.players.size() * 30 / 100 + 0.4);
        for (int i = 0; i < (int) round; i++) {
            Random r = new Random();
            int index = r.nextInt(PlayerListener.players.size());
            Player p = PlayerListener.players.get(index);
            if (!PlayerListener.tntplayers.contains(p)) {
                PlayerListener.tntplayers.add(p);
                p.getInventory().clear();
                p.getInventory().setHelmet(new ItemStack(Material.TNT));
                p.getInventory().addItem(new ItemStack(Material.TNT));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10000, 3));
                Bukkit.broadcastMessage("§7" + p.getName() + " la tiene! :v");
                MessagesController.sendTitle(p, " ", "§e¡§cTú §ela tienes!", 5, 20, 5);
                NametagEdit.getApi().setPrefix(p, "§c[TNT] §a");
            }
        }
    }
}
