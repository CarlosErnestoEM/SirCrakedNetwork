package com.pollogamer.sircrakedserver.vanish;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class Vanish implements Listener {

    public Vanish() {
        Bukkit.getPluginManager().registerEvents(this, SirCrakedCore.getCore());
    }

    public static List<Player> vanishedplayers = new ArrayList<>();

    public static boolean isVanished(Player player) {
        return vanishedplayers.contains(player);
    }

    public static void toggleVanish(Player player) {
        if (isVanished(player)) {
            disableVanish(player);
        } else {
            setVanish(player);
        }
    }

    public static void setVanish(Player player) {
        vanishedplayers.add(player);
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (!player1.hasPermission("sircraked.vanish.bypass")) player1.hidePlayer(player);
        }
        player.sendMessage(Lang.prefix + "Ahora eres invisible!");
    }

    public static void disableVanish(Player player) {
        if (vanishedplayers.contains(player)) vanishedplayers.remove(player);
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.showPlayer(player);
        }
        player.sendMessage(Lang.prefix + "Ya no eres invisible! xd");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (isVanished(event.getPlayer())) {
            disableVanish(event.getPlayer());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPermission("sircraked.vanish.bypass")) {
            for (Player player : vanishedplayers) {
                event.getPlayer().hidePlayer(player);
            }
        }
    }

}
