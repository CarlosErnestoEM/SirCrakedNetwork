package com.pollogamer.uhcsimulator.objects;

import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.manager.MySQLManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class UHCPlayer {

    private static Map<Player, UHCPlayer> uhcplayer = new HashMap<>();

    public static UHCPlayer getUHCPlayer(Player player) {
        if (uhcplayer.containsKey(player)) {
            return uhcplayer.get(player);
        } else {
            return new UHCPlayer(player);
        }
    }

    public static void removeUHCPlayer(Player player) {
        if (uhcplayer.containsKey(player)) {
            uhcplayer.remove(player);
        }
    }

    private Player player;
    private int wins;
    private int played;
    private int kills;
    private int elo;

    private UHCPlayer(Player player) {
        this.player = player;
        uhcplayer.put(player, this);
        init();
    }

    public void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                wins = MySQLManager.getData(player, "wins");
                played = MySQLManager.getData(player, "played");
                kills = MySQLManager.getData(player, "kills");
                elo = MySQLManager.getData(player, "elo");
            }
        }.runTaskAsynchronously(Principal.getPlugin());
    }

    public Player getPlayer() {
        return player;
    }

    public int getWins() {
        return wins;
    }

    public int getPlayed() {
        return played;
    }

    public int getKills() {
        return kills;
    }

    public int getElo() {
        return elo;
    }
}
