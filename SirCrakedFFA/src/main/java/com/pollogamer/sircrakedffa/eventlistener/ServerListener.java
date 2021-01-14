package com.pollogamer.sircrakedffa.eventlistener;

import com.pollogamer.sircrakedffa.Principal;
import com.pollogamer.sircrakedffa.manager.Lang;
import com.pollogamer.sircrakedserver.utils.MessagesController;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ServerListener implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void NivelDeHambre(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void disablecraft(CraftItemEvent event) {
        if (!event.getWhoClicked().hasPermission("sircraked.craft")) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }

    @EventHandler
    public void RomperBloque(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player p = event.getPlayer();
        if (!p.hasPermission("sircraked.romper")) {
            if (block.getTypeId() == 51) {
                event.setCancelled(false);
                return;
            } else {
                event.setCancelled(true);
                p.sendMessage("§4§lSir§1§lCraked §7§l» §cNo puedes romper eso");
            }
        }
    }

    @EventHandler
    public void Caida(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                event.setCancelled(true);
                Player p = (Player) event.getEntity();
                p.getInventory().remove(Material.PAPER);
                p.getInventory().remove(Material.NETHER_STAR);
            }
        }
    }

    @EventHandler
    public void Lluvia(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if ((p.getWorld() == Lang.buildffa.getWorld()) && ((event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) || (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerShot(EntityDamageByEntityEvent entity) {
        if ((entity.getDamager() instanceof Arrow)) {
            Arrow arrow = (Arrow) entity.getDamager();
            if ((arrow.getShooter() instanceof Player)) {
                Player player = (Player) arrow.getShooter();
                Damageable obj = (Damageable) entity.getEntity();
                if ((obj instanceof Player)) {
                    Player v = (Player) obj;
                    double ptviev = obj.getHealth();
                    Integer damage = (int) entity.getFinalDamage();
                    if (!obj.isDead()) {
                        Integer realHealth = (int) (ptviev - damage);
                        if (realHealth > 0) {
                            MessagesController.sendActionBar(player, "§4§lSir§1§lCraked §aFFA §7§l» §c" + v.getName() + ChatColor.AQUA + " esta a " + ChatColor.RED + realHealth / 2.0F + ChatColor.AQUA + "§4❤");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        final Location b = block.getLocation();
        final Material bp = event.getBlockReplacedState().getType();
        if ((player.getGameMode() == GameMode.SURVIVAL) && (player.getWorld() == Lang.buildffa.getWorld())) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Principal.getPlugin(), () -> b.getBlock().setType(bp), 100L);
        }
    }

}
