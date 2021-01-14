package me.felipefonseca.plugins.listeners;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.manager.GameState;
import me.felipefonseca.plugins.utils.MessagesLoader;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class GameListener implements Listener {

    private final Main plugin;
    private final HashMap<Location, Block> blocksPlaced = new HashMap();

    public GameListener(Main main) {
        this.plugin = main;
    }

    public void init() {
        this.blocksPlaced.clear();
    }

    @EventHandler
    public void onMotd(ServerListPingEvent serverListPingEvent) {
        switch (GameState.state) {
            case WAITING: {
                serverListPingEvent.setMotd(MessagesLoader.getWaitingState());
                break;
            }
            case STARTING: {
                serverListPingEvent.setMotd(MessagesLoader.getStartingState());
                break;
            }
            case IN_GAME: {
                serverListPingEvent.setMotd(MessagesLoader.getIngameState());
                break;
            }
            case DEATHMATCH: {
                serverListPingEvent.setMotd(MessagesLoader.getDeathMatchState());
                break;
            }
            case ENDING: {
                serverListPingEvent.setMotd(MessagesLoader.getEndingState());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent) {
        if (this.plugin.getGameManager().isInGame() && playerInteractEvent.getAction().equals((Object) Action.LEFT_CLICK_BLOCK) && playerInteractEvent.getClickedBlock() != null && this.blocksPlaced.containsKey((Object) playerInteractEvent.getClickedBlock().getLocation()) && this.blocksPlaced.containsValue((Object) playerInteractEvent.getClickedBlock())) {
            if (playerInteractEvent.getClickedBlock().getType() == Material.WOOD) {
                playerInteractEvent.getClickedBlock().setType(Material.AIR);
                playerInteractEvent.getClickedBlock().getDrops().clear();
                playerInteractEvent.getClickedBlock().getWorld().dropItem(playerInteractEvent.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.WOOD, 1)).setVelocity(new Vector(0, 0, 0));
            } else if (playerInteractEvent.getClickedBlock().getType() == Material.COBBLESTONE) {
                playerInteractEvent.getClickedBlock().setType(Material.AIR);
                playerInteractEvent.getClickedBlock().getDrops().clear();
                playerInteractEvent.getClickedBlock().getWorld().dropItem(playerInteractEvent.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.COBBLESTONE, 1)).setVelocity(new Vector(0, 0, 0));
            }
        }
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent playerItemConsumeEvent) {
        if (playerItemConsumeEvent.getItem().equals((Object) this.plugin.getGameManager().getManzana())) {
            playerItemConsumeEvent.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1), true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player p = event.getEntity();
        if (this.plugin.getGameManager().isInGame()) {
            if (p.getKiller() instanceof Player) {
                Player killer = p.getKiller();
                p.getWorld().strikeLightningEffect(p.getLocation());
                this.plugin.getGameManager().getPlayersInGame().remove(p);
                this.plugin.getMessageSender().sendMessage(killer, "&cMataste a &b" + p.getName() + "&c!");
                this.plugin.getMessageSender().sendBroadcast("&6" + p.getName() + " &afue violado por &6" + killer.getName() + " &aQuedan " + plugin.getGameManager().getPlayersInGame().size() + " jugadores en juego!");
                this.plugin.getPlayerManager().setSpectator(p);
                killer.getInventory().addItem(this.plugin.getGameManager().getManzana());
                this.plugin.getPlayerManager().addKillToPlayer(killer);
            } else {
                this.plugin.getMessageSender().sendMessage(p, "&cValiste verga! xD");
                p.getWorld().strikeLightningEffect(p.getLocation());
                this.plugin.getGameManager().getPlayersInGame().remove(p);
                this.plugin.getMessageSender().sendBroadcast("&6" + p.getName() + " &avalio verga! Quedan " + plugin.getGameManager().getPlayersInGame().size() + " jugadores en juego!");
                this.plugin.getPlayerManager().setSpectator(p);
            }
            this.plugin.getGameManager().checkWinner();
            this.plugin.getGameManager().checkDm();
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent.getEntity() instanceof Player) {
            if (this.plugin.getGameManager().isInGame() || this.plugin.getGameManager().isNotPVP()) {
                entityDamageEvent.setCancelled(false);
            } else {
                entityDamageEvent.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.getEntity() instanceof Player) {
            if (this.plugin.getGameManager().isInGame() || this.plugin.getGameManager().isNotPVP()) {
                entityDamageByEntityEvent.setCancelled(false);
            } else {
                entityDamageByEntityEvent.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent entityDamageByBlockEvent) {
        if (entityDamageByBlockEvent.getEntity() instanceof Player) {
            if (this.plugin.getGameManager().isInGame() || this.plugin.getGameManager().isNotPVP()) {
                entityDamageByBlockEvent.setCancelled(false);
            } else {
                entityDamageByBlockEvent.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        blockBreakEvent.getBlock().getDrops().clear();
        if (this.plugin.getGameManager().isInGame()) {
            if (blockBreakEvent.getBlock().getType().equals(Material.WOOD) || blockBreakEvent.getBlock().getType().equals((Object) Material.COBBLESTONE) || blockBreakEvent.getBlock().getType().equals((Object) Material.LEAVES) && this.blocksPlaced.containsKey((Object) blockBreakEvent.getBlock().getLocation()) && this.blocksPlaced.containsValue((Object) blockBreakEvent.getBlock())) {
                blockBreakEvent.setCancelled(false);
                this.blocksPlaced.remove(blockBreakEvent.getBlock().getLocation(), blockBreakEvent.getBlock());
            } else {
                blockBreakEvent.setCancelled(true);
            }
        } else {
            blockBreakEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        if (this.plugin.getGameManager().isInGame()) {
            if (blockPlaceEvent.getBlock().getType().equals(Material.WOOD) || blockPlaceEvent.getBlock().getType().equals((Object) Material.COBBLESTONE) || blockPlaceEvent.getBlock().getType().equals((Object) Material.LAVA) || blockPlaceEvent.getBlock().getType().equals((Object) Material.WATER) || blockPlaceEvent.getBlock().getType().equals((Object) Material.WORKBENCH)) {
                blockPlaceEvent.setCancelled(false);
                this.blocksPlaced.put(blockPlaceEvent.getBlock().getLocation(), blockPlaceEvent.getBlock());
            } else {
                blockPlaceEvent.setCancelled(true);
            }
        } else {
            blockPlaceEvent.setCancelled(true);
        }
    }

    public void registerGameEvents() {
        this.plugin.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this.plugin);
    }

    public void unregisterGameEvents() {
        HandlerList.unregisterAll((Listener) this);
    }

}

