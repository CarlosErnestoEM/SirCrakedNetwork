package com.pollogamer.tnttag.listener;

import com.minebone.itemstack.ItemStackBuilder;
import com.nametagedit.plugin.NametagEdit;
import com.pollogamer.tnttag.Principal;
import com.pollogamer.tnttag.extras.Lang;
import com.pollogamer.tnttag.extras.MessagesController;
import com.pollogamer.tnttag.extras.MySQLCheck;
import com.pollogamer.tnttag.extras.Utils;
import com.pollogamer.tnttag.inv.Teleporter;
import com.pollogamer.tnttag.manager.PlayerManager;
import com.pollogamer.tnttag.task.LobbyTask;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerListener implements Listener {

    public static List<Player> tntplayers = new CopyOnWriteArrayList<>();
    public static List<Player> players = new CopyOnWriteArrayList<>();
    public static List<Player> spectators = new CopyOnWriteArrayList<>();
    public static Map<Player, Integer> coins = new HashMap<>();

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (Lang.started) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                e.setDamage(0);
            } else {
                e.setDamage(0);
            }
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage2(EntityDamageByEntityEvent e) throws Exception {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        Player entity = (Player) e.getEntity();
        Player damage = (Player) e.getDamager();
        if (PlayerListener.spectators.contains(damage)) {
            e.setCancelled(true);
            return;
        }
        if (tntplayers.contains(damage)) {
            if (tntplayers.contains(entity)) return;
            e.setDamage(0);
            tntplayers.remove(damage);
            tntplayers.add(entity);
            damage.getInventory().setHelmet(null);
            damage.removePotionEffect(PotionEffectType.SPEED);
            damage.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000 * 20, 1));
            NametagEdit.getApi().setPrefix(damage, "§a");
            damage.getInventory().clear();
            entity.getInventory().clear();
            entity.removePotionEffect(PotionEffectType.SPEED);
            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10000, 3));
            entity.getInventory().setHelmet(new ItemStack(Material.TNT));
            entity.getInventory().addItem(new ItemStack(Material.TNT));
            MessagesController.sendTitle(entity, " ", "§e¡§cTú §ela tienes!", 5, 20, 5);
            Bukkit.broadcastMessage("§7" + entity.getName() + " la tiene! :v");
            NametagEdit.getApi().setPrefix(entity, "§c[TNT] §a");
            PlayerManager.launchFirework(entity);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("tntrun.bypass")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getPlayer().hasPermission("tntrun.bypass")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onMove(InventoryClickEvent e) {
        if (e.getCurrentItem().getType().equals(Material.AIR)) return;
        if (e.getCurrentItem().getType().equals(Material.TNT)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        new MySQLCheck(e.getPlayer());
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setHelmet(null);
        e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
        e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        e.getPlayer().setLevel(0);
        e.getPlayer().getInventory().setItem(8, new ItemStackBuilder(Material.BED).setName("§c§lRegresar al Lobby §7(Click Derecho)"));
        NametagEdit.getApi().setPrefix(e.getPlayer(), "§a");
        if (Lang.started) {
            PlayerManager.setSpectator(e.getPlayer());
            Principal.playerManager.setScoreboardInGame(e.getPlayer());
        } else {
            PlayerListener.players.add(e.getPlayer());
            e.getPlayer().teleport(Lang.spawnlobby);
            e.getPlayer().setGameMode(GameMode.ADVENTURE);
            Principal.playerManager.setScoreboardLobbyWaiting(e.getPlayer());
            Bukkit.broadcastMessage("§a" + e.getPlayer().getName() + " §eentro al desvergue! §e(" + PlayerListener.players.size() + "/" + Bukkit.getMaxPlayers() + ")");
            if (PlayerListener.players.size() >= Lang.minplayers) {
                if (Lang.starting) return;
                if (Lang.forcestart) return;
                Lang.starting = true;
                new LobbyTask();
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
        Utils.coinsToMySQL(event.getPlayer());
        if (PlayerListener.players.contains(event.getPlayer())) {
            PlayerListener.players.remove(event.getPlayer());
            if (!Lang.started) {
                Bukkit.broadcastMessage("§a" + event.getPlayer().getName() + " §esalio del desvergue! §e(" + PlayerListener.players.size() + "/" + Bukkit.getMaxPlayers() + ")");
            }
        }
        if (PlayerListener.tntplayers.contains(event.getPlayer())) {
            PlayerListener.tntplayers.remove(event.getPlayer());
        }
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
}
