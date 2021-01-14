package com.pollogamer.uhcsimulator.spectator;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.sircrakedserver.staffmode.StaffInvsee;
import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpectatorManager implements Listener {

    private List<Player> vanishedplayers = new ArrayList<>();
    private ItemStackBuilder spectatoritem = new ItemStackBuilder(Material.COMPASS).setName("&aEspectear");
    private ItemStackBuilder openinv = new ItemStackBuilder(Material.BOOK).setName("&eVer inventario xd");
    private ItemStackBuilder back = new ItemStackBuilder(Material.BED).setName("&cRegresar a la lobby (Click derecho)");
    private ItemStackBuilder arenapvp = new ItemStackBuilder(Material.DIAMOND_SWORD).setName("&aRegresar a ArenaPvP");

    public SpectatorManager() {
        Bukkit.getPluginManager().registerEvents(this, Principal.getPlugin());
    }

    public void setSpectator(Player player) {
        PlayerManager.setCleanPlayer(player);
        Lang.spectators.add(player);
        vanishPlayer(player);
        player.setGameMode(GameMode.ADVENTURE);
        player.spigot().setCollidesWithEntities(false);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setHealth(20);
        new BukkitRunnable() {
            @Override
            public void run() {
                player.getInventory().setItem(0, spectatoritem);
                player.getInventory().setItem(1, openinv);
                player.getInventory().setItem(4, arenapvp);
                player.getInventory().setItem(8, back);
                player.updateInventory();
            }
        }.runTaskLater(Principal.getPlugin(), 1);
    }

    public void vanishPlayer(Player player) {
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.hidePlayer(player);
        }
        vanishedplayers.add(player);
    }

    public void showPlayers(Player player) {
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.showPlayer(player);
        }
    }

    public void removeSpectator(Player player) {
        vanishedplayers.remove(player);
        Lang.spectators.remove(player);
        player.spigot().setCollidesWithEntities(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        PlayerManager.setCleanPlayer(player);
        showPlayers(player);
    }

    public boolean isSpectator(Player player) {
        return Lang.spectators.contains(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        for (Player player : vanishedplayers) {
            event.getPlayer().hidePlayer(player);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (isSpectator(event.getPlayer())) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem();
            if (item != null) {
                if (item.hasItemMeta()) {
                    String name = item.getItemMeta().getDisplayName();
                    if (name != null) {
                        if (name.equals(spectatoritem.getItemMeta().getDisplayName())) {
                            new SpectatorGUI(player);
                        } else if (name.equals(back.getItemMeta().getDisplayName())) {
                            Bukkit.dispatchCommand(player, "leave");
                        } else if (name.equals(arenapvp.getItemMeta().getDisplayName())) {
                            PlayerManager.sendToServer(player, "ArenaPvP");
                        }
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract2(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            Player player = event.getPlayer();
            Player target = (Player) event.getRightClicked();
            if (isSpectator(player)) {
                ItemStack itemStack = event.getPlayer().getItemInHand();
                if (itemStack != null) {
                    if (itemStack.hasItemMeta()) {
                        String name = itemStack.getItemMeta().getDisplayName();
                        if (name != null) {
                            if (name.equals(openinv.getItemMeta().getDisplayName())) {
                                new StaffInvsee(player, target);
                            }
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void levelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (isSpectator(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (isSpectator(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage2(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (isSpectator(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if (isSpectator(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (isSpectator(player)) {
            if (event.getClickedInventory() != null) {
                if (event.getClickedInventory().getType().equals(InventoryType.CHEST)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
