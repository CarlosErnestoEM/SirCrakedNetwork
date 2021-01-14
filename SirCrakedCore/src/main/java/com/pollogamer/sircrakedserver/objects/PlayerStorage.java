package com.pollogamer.sircrakedserver.objects;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerStorage {

    private Map<Player, Double> health = new HashMap<>();
    private Map<Player, Integer> feed = new HashMap<>();
    private Map<Player, GameMode> gamemode = new HashMap<>();
    private Map<Player, ItemStack[]> inventory = new HashMap<>();
    private Map<Player, ItemStack[]> armor = new HashMap<>();
    private Map<Player, Location> locations = new HashMap<>();
    private Map<Player, Float> xp = new HashMap<>();

    public void saveOldData(Player player) {
        health.put(player, player.getHealth());
        feed.put(player, player.getFoodLevel());
        gamemode.put(player, player.getGameMode());
        inventory.put(player, player.getInventory().getContents());
        armor.put(player, player.getInventory().getArmorContents());
        locations.put(player, player.getLocation());
        xp.put(player, player.getExp());
    }

    public void setData(Player player, boolean maxhealth) {
        if (maxhealth) {
            player.setHealth(20);
        } else {
            player.setHealth(health.get(player));
        }
        player.setFoodLevel(feed.get(player));
        player.setGameMode(gamemode.get(player));
        player.getInventory().setContents(inventory.get(player));
        player.getInventory().setArmorContents(armor.get(player));
        player.teleport(locations.get(player));
        player.setExp(xp.get(player));
    }

    public boolean existData(Player player) {
        return health.containsKey(player);
    }

    public void tryEraseData(Player player) {
        if (existData(player)) {
            eraseData(player);
        }
    }

    public void eraseData(Player player) {
        health.remove(player);
        feed.remove(player);
        gamemode.remove(player);
        inventory.remove(player);
        armor.remove(player);
        locations.remove(player);
        xp.remove(player);
    }
}
