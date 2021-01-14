package com.pollogamer.sircrakedserver.objects;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.manager.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class SirPlayer extends BukkitRunnable {

    private static Map<String, SirPlayer> players = new HashMap<>();

    public SirPlayer(Player player) {
        this.player = player;
        players.put(player.getName(), this);
        runTaskTimer(SirCrakedCore.getCore(), 2, 60);
    }

    private Player player;
    private int coins;
    private double level;


    @Override
    public void run() {
        startAll();
    }

    public void startAll() {
        if (player.isOnline()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    coins = PlayerInfo.getCoins(player.getName());
                    level = PlayerInfo.getLevel(player.getName());
                }
            }.runTaskAsynchronously(SirCrakedCore.getCore());
        } else {
            this.cancel();
            return;
        }
    }


    public static SirPlayer getPlayer(Player player) {
        if (!players.containsKey(player.getName())) {
            return new SirPlayer(player);
        } else if (players.containsKey(player.getName())) {
            return players.get(player.getName());
        }
        return null;
    }

    public static void unregisterPlayer(Player customplayer) {
        if (players.containsKey(customplayer.getName())) {
            players.remove(getPlayer(customplayer));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public double getLevel() {
        return level;
    }

}
