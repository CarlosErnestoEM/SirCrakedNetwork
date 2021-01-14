package com.pollogamer.tntrun.listener;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.tntrun.Principal;
import com.pollogamer.tntrun.extras.Lang;
import com.pollogamer.tntrun.extras.MySQLCheck;
import com.pollogamer.tntrun.extras.Utils;
import com.pollogamer.tntrun.inv.Teleporter;
import com.pollogamer.tntrun.manager.PlayerManager;
import com.pollogamer.tntrun.task.EndingTask;
import com.pollogamer.tntrun.task.LobbyTask;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerListener implements Listener {

    public static List<Player> players = new ArrayList<>();
    public static List<Player> spectators = new ArrayList<>();
    public static Map<Player, Integer> coins = new HashMap<>();
    public static Map<Player, Integer> doublejump = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        doublejump.put(p, 1);
        event.setJoinMessage(null);
        PlayerManager.setCleanPlayer(p);
        new MySQLCheck(event.getPlayer());
        if (!Lang.started) {
            PlayerManager.setScoreboardWaiting(p);
            players.add(p);
            p.teleport(Lang.spawn);
            p.getInventory().setItem(8, new ItemStackBuilder(Material.BED).setName("§c§lRegresar al Lobby §7(Click Derecho)"));
            Bukkit.broadcastMessage("§a" + p.getName() + " §eentro al desvergue! §e(§b" + PlayerListener.players.size() + "§e/§b" + Bukkit.getMaxPlayers() + "§e)!");
            if (PlayerListener.players.size() >= Lang.minplayers) {
                if (Lang.starting) return;
                if (Lang.forcestart) return;
                Lang.starting = true;
                new LobbyTask();
            }
        } else {
            PlayerManager.setScoreboardGame(p);
            PlayerManager.setSpectator(p);
        }
    }

    @EventHandler
    public void onMove(InventoryClickEvent e) {
        if (e.getWhoClicked().hasPermission("tnttag.byppas")) return;
        if (e.getCurrentItem().getType().equals(Material.AIR)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        event.setCancelled(true);
        if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            if (!PlayerListener.players.contains(event.getEntity())) return;
            PlayerManager.setSpectator((Player) event.getEntity());
            Bukkit.broadcastMessage("§7" + event.getEntity().getName() + " §cvalio verga! §e" + PlayerListener.players.size() + (PlayerListener.players.size() != 1 ? " §cjugadores restantes!" : " §cjugador restante!"));
            if (PlayerListener.players.size() == 1) {
                Lang.tnt = false;
                new EndingTask();
            }
        }
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("tntrun.bypass")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!Lang.tnt) return;
        if (PlayerListener.spectators.contains(e.getPlayer())) return;
        Player p = e.getPlayer();
        Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
        Block tnt = b.getRelative(BlockFace.DOWN);
        if ((p.getGameMode() == GameMode.ADVENTURE) || (p.getGameMode() == GameMode.SURVIVAL)) {
            if ((b.getType().equals(Material.SAND)) || (b.getType().equals(Material.GRAVEL))) {
                new BukkitRunnable() {
                    public void run() {
                        b.setType(Material.AIR);
                        tnt.setType(Material.AIR);
                    }
                }.runTaskLater(Principal.plugin, 8L);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.hasItem()) return;
        if (!event.getItem().hasItemMeta()) return;
        if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("§cEspectear")) {
            new Teleporter(Principal.plugin, event.getPlayer());
        } else if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("§c§lRegresar al Lobby §7(Click Derecho)")) {
            PlayerManager.sendToServer(event.getPlayer(), "Hub-1");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("sircraked.color")) {
            event.setFormat(" §a%1$s§f: §c%2$s");
        } else {
            event.setFormat(" §a%1$s§f: %2$s");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        if (PlayerListener.players.contains(event.getPlayer())) {
            PlayerListener.players.remove(event.getPlayer());
            if (!Lang.started) {
                Bukkit.broadcastMessage("§a" + event.getPlayer().getName() + " §esalio del desvergue! §e(§b" + PlayerListener.players.size() + "§e/§b" + Bukkit.getMaxPlayers() + "§e)!");
            } else {
                Bukkit.broadcastMessage("§7" + event.getPlayer().getName() + " §cvalio verga! §e" + PlayerListener.players.size() + (PlayerListener.players.size() != 1 ? " §cjugadores restantes!" : " §cjugador restante!"));
                if (PlayerListener.players.size() == 1) {
                    new EndingTask();
                }
            }
        }
        Utils.coinsToMySQL(event.getPlayer());
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        if (Lang.starting) {
            event.setMotd("§9Empezando...");
        } else if (Lang.started) {
            event.setMotd("§cEn Juego...");
        } else {
            event.setMotd("§aEsperando...");
        }
    }

    @EventHandler
    public void onPlayerFly(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        if (!Lang.started) return;
        if (Lang.starting) return;
        if (!players.contains(e.getPlayer())) return;
        if (p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            p.setAllowFlight(false);
            p.setFlying(false);
            p.setVelocity(p.getLocation().getDirection().multiply(1.5D).setY(0.5D));
            p.playEffect(p.getLocation(), Effect.BLAZE_SHOOT, 15);
            doublejump.put(p, doublejump.get(p) - 1);
        }
    }

    @EventHandler
    public void onPlayerMove2(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Lang.starting) return;
        if (!Lang.started) return;
        if (!players.contains(e.getPlayer())) return;
        if ((e.getPlayer().getGameMode() != GameMode.CREATIVE)) {
            if (doublejump.get(p) >= 1) {
                p.setAllowFlight(true);
            }
        }
    }
}
