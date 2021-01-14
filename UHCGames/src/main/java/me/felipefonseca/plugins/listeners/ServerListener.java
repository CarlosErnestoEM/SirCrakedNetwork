package me.felipefonseca.plugins.listeners;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.utils.MessagesLoader;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

public class ServerListener implements Listener {

    private final Main plugin;

    public ServerListener(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent) {
        if (this.plugin.getGameManager().isWaiting() && (playerInteractEvent.getAction().equals((Object) Action.RIGHT_CLICK_AIR) || playerInteractEvent.getAction().equals((Object) Action.RIGHT_CLICK_BLOCK)) && playerInteractEvent.getPlayer().getItemInHand() != null && playerInteractEvent.getPlayer().getItemInHand().getType().equals((Object) Material.BED)) {
            this.plugin.getPlayerManager().sendToServer(playerInteractEvent.getPlayer());
            this.plugin.getMessageSender().sendMessage(playerInteractEvent.getPlayer(), MessagesLoader.getGotToLobby());
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent blockIgniteEvent) {
        blockIgniteEvent.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent weatherChangeEvent) {
        if (weatherChangeEvent.toWeatherState()) {
            weatherChangeEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent playerDropItemEvent) {
        if (this.plugin.getGameManager().isWaiting() || this.plugin.getGameManager().isFinished()) {
            playerDropItemEvent.setCancelled(true);
        } else {
            playerDropItemEvent.setCancelled(false);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent playerPickupItemEvent) {
        if (this.plugin.getGameManager().isWaiting() || this.plugin.getGameManager().isFinished()) {
            playerPickupItemEvent.setCancelled(true);
        } else {
            playerPickupItemEvent.setCancelled(false);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent foodLevelChangeEvent) {
        if (this.plugin.getGameManager().isWaiting() || this.plugin.getGameManager().isFinished()) {
            foodLevelChangeEvent.setCancelled(true);
        } else {
            foodLevelChangeEvent.setCancelled(false);
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent leavesDecayEvent) {
        leavesDecayEvent.setCancelled(true);
    }

    public void registerServerEvents() {
        this.plugin.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this.plugin);
    }
}

