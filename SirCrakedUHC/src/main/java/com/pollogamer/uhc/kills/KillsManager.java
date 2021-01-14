package com.pollogamer.uhc.kills;

import com.pollogamer.uhc.Principal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Map;

public class KillsManager implements Listener {

    private Map<Player, Integer> kills = new HashMap<>();

    public KillsManager() {
        Bukkit.getPluginManager().registerEvents(this, Principal.getPlugin());
    }

    public int getKills(Player player) {
        try {
            return kills.get(player);
        } catch (Exception e) {
            return 0;
        }
    }

    public void addKill(Player player) {
        try {
            kills.put(player, (kills.get(player) + 1));
        } catch (Exception e) {
            kills.put(player, 1);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            addKill(event.getEntity().getKiller());
        }
    }


}
