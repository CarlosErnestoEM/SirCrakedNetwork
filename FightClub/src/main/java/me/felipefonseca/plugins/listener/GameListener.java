package me.felipefonseca.plugins.listener;

import me.felipefonseca.plugins.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class GameListener
        implements Listener {
    private final Main plugin;
    private BukkitTask fallControl;

    public GameListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMotdChange(ServerListPingEvent e) {
        if (this.plugin.getGameManager().isInLobby()) {
            e.setMotd("§cEsperando...");
        } else if (this.plugin.getGameManager().isInGame()) {
            e.setMotd("\u00a7cEn juego");
        } else if (this.plugin.getGameManager().isInDeathMatch()) {
            e.setMotd("\u00a7cDeathmatch");
        } else if (this.plugin.getGameManager().isEnding()) {
            e.setMotd("\u00a7cReiniciando...");
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (this.plugin.getGameManager().isInGame() || this.plugin.getGameManager().isInDeathMatch()) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent e) {
        if (this.plugin.getGameManager().isInGame() || this.plugin.getGameManager().isInDeathMatch()) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (this.plugin.getGameManager().isInGame() || this.plugin.getGameManager().isInDeathMatch()) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (this.plugin.getGameManager().isInGame()) {
            this.plugin.getArenaManager().getBlocksPlaced().put(e.getBlock().getLocation(), e.getBlock());
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (this.plugin.getGameManager().isInGame() && this.plugin.getArenaManager().getBlocksPlaced().containsKey((Object) e.getBlock().getLocation())) {
            e.setCancelled(false);
        } else {
            this.plugin.getArenaManager().getBlocksBreaked().add(e.getBlock().getState());
        }
    }

    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this.plugin);
        if (this.fallControl == null) {
            this.fallControl = this.plugin.getServer().getScheduler().runTaskTimer((Plugin) this.plugin, () -> {
                        this.plugin.getServer().getOnlinePlayers().stream().filter(players -> this.plugin.getGameManager().getSpectators().contains((Object) players)).filter(players -> players.getLocation().getBlockY() <= 0).forEach(players -> {
                                    players.teleport(this.plugin.getArenaManager().getLobby());
                                }
                        );
                    }
                    , 20, 20);
        }
    }

    public void unregisterAllGameEvents() {
        HandlerList.unregisterAll((Listener) this);
        this.fallControl.cancel();
    }
}

