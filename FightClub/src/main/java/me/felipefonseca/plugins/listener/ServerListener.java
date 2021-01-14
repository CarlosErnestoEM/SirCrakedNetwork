package me.felipefonseca.plugins.listener;

import me.felipefonseca.plugins.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

public class ServerListener
        implements Listener {
    private final Main plugin;

    public ServerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        if (this.plugin.getGameManager().isInLobby()) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent e) {
        if (this.plugin.getGameManager().isInLobby()) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onPlayerFoodLevel(FoodLevelChangeEvent e) {
        if (this.plugin.getGameManager().isInLobby()) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this.plugin);
    }

    public void unregisterAllServerEvents() {
        HandlerList.unregisterAll((Listener) this);
    }
}

