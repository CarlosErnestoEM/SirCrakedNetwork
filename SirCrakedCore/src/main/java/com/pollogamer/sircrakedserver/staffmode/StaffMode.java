package com.pollogamer.sircrakedserver.staffmode;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.gui.OnlinePlayers;
import com.pollogamer.sircrakedserver.utils.Lang;
import com.pollogamer.sircrakedserver.vanish.Vanish;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffMode implements Listener {

    public static List<Player> staffplayers = new ArrayList<>();

    public static ItemStackBuilder compass = new ItemStackBuilder(Material.COMPASS).setName("&aEspectear jugadores");

    public static ItemStackBuilder vanishitem = new ItemStackBuilder(Material.EYE_OF_ENDER).setName("&aActivar/Desactivar invisiblidad xd");

    public static ItemStackBuilder punishitem = new ItemStackBuilder(Material.INK_SACK).setStackData((short) 9).setName("&eAbrir menu de sanciones 7u7");

    public static ItemStackBuilder trollitem = new ItemStackBuilder(Material.BLAZE_ROD).setName("&eBarita magita (Trollear :v)");

    public static ItemStackBuilder seeinv = new ItemStackBuilder(Material.BOOK).setName("&eMostrar inventario :v");
    public static ItemStackBuilder freeze = new ItemStackBuilder(Material.PACKED_ICE).setName("&aCongelar/Descongelar xd");

    public static Map<Player, ItemStack[]> inventories = new HashMap<>();
    public static Map<Player, ItemStack[]> armor = new HashMap<>();
    public static Map<Player, GameMode> gamemodes = new HashMap<>();

    public StaffMode() {
        Bukkit.getPluginManager().registerEvents(this, SirCrakedCore.getCore());
        SirCrakedCore.getCore().getCommand("stp").setExecutor(new CMDStp());
        SirCrakedCore.getCore().getCommand("ss").setExecutor(new CMDStaffMode());
    }

    public static void toggleStaffMode(Player player) {
        if (isEnabled(player)) {
            disableStaffMode(player);
        } else {
            enableStaffMode(player);
        }
    }

    public static void enableStaffMode(Player player) {
        staffplayers.add(player);
        player.sendMessage(Lang.prefix + "Has activado el modo staff xd");
        inventories.put(player, player.getInventory().getContents());
        armor.put(player, player.getInventory().getArmorContents());
        gamemodes.put(player, player.getGameMode());
        setInventory(player);
        Vanish.setVanish(player);
        player.spigot().setCollidesWithEntities(false);
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setAllowFlight(true);
        player.setFlying(true);
    }


    public static void disableStaffMode(Player player) {
        staffplayers.remove(player);
        player.sendMessage(Lang.prefix + "Has abandonado el modo de staff xd");
        player.getInventory().setContents(inventories.get(player));
        player.getInventory().setArmorContents(armor.get(player));
        player.setGameMode(gamemodes.get(player));
        inventories.remove(player);
        armor.remove(player);
        gamemodes.remove(player);
        Vanish.disableVanish(player);
        player.spigot().setCollidesWithEntities(true);
        if (!(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

    public static boolean isEnabled(Player player) {
        return staffplayers.contains(player);
    }

    public static void setInventory(Player player) {
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();
        player.getInventory().setItem(0, compass);
        player.getInventory().setItem(1, vanishitem);
        player.getInventory().setItem(3, punishitem);
        player.getInventory().setItem(5, trollitem);
        player.getInventory().setItem(7, seeinv);
        player.getInventory().setItem(8, freeze);
    }

    @EventHandler
    public void checkAction(PlayerInteractAtEntityEvent event) {
        if (isEnabled(event.getPlayer())) {
            if (event.getRightClicked() instanceof Player) {
                Player player = event.getPlayer();
                Player obj = (Player) event.getRightClicked();
                if (!isEnabled(obj)) {
                    ItemStack handd = player.getItemInHand();
                    if (handd != null) {
                        String hand = handd.getItemMeta().getDisplayName();
                        if (hand != null) {
                            if (hand.equals(seeinv.getItemMeta().getDisplayName())) {
                                new StaffInvsee(player, obj);
                                //player.openInventory(new InvseePlayerInventory(obj).getBukkitInventory());
                            } else if (hand.equals(freeze.getItemMeta().getDisplayName())) {
                                Bukkit.dispatchCommand(player, "fz " + obj.getName());
                            } else if (hand.equals(punishitem.getItemMeta().getDisplayName())) {
                                Bukkit.dispatchCommand(player, "s " + obj.getName());
                            } else if (hand.equals(trollitem.getItemMeta().getDisplayName())) {
                                Bukkit.dispatchCommand(player, "troll " + obj.getName());
                            }
                        }
                    }
                    event.setCancelled(true);
                } else {
                    player.sendMessage(Lang.prefix + "No puedes hacer eso krnal! xdxdxd");
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (isEnabled(event.getPlayer())) {
            ItemStack item = event.getItem();
            if (item != null) {
                String name = item.getItemMeta().getDisplayName();
                if (name != null) {
                    if (name.equals(vanishitem.getItemMeta().getDisplayName())) {
                        Vanish.toggleVanish(event.getPlayer());
                    } else if (name.equals(compass.getItemMeta().getDisplayName())) {
                        new OnlinePlayers(event.getPlayer());
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (isEnabled(event.getPlayer())) {
            disableStaffMode(event.getPlayer());
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (isEnabled(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (isEnabled(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (isEnabled(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (isEnabled(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void oNDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Player target = (Player) event.getEntity();
            if (isEnabled(player)) {
                event.setCancelled(true);
            } else if (isEnabled(target)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (isEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamag2e(EntityDamageByBlockEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (isEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void feedLevel(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (isEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMoveInventory(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (isEnabled(player)) {
                if (event.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
