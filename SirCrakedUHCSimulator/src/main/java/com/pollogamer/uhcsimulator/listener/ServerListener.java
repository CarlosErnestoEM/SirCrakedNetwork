package com.pollogamer.uhcsimulator.listener;

import com.github.cheesesoftware.PowerfulPermsAPI.PermissionManager;
import com.github.cheesesoftware.PowerfulPermsAPI.PowerfulPermsPlugin;
import com.pollogamer.sircrakedserver.utils.MessagesController;
import com.pollogamer.uhcsimulator.extras.Lang;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ServerListener implements Listener {

    private PowerfulPermsPlugin pp = (PowerfulPermsPlugin) Bukkit.getPluginManager().getPlugin("PowerfulPerms");
    private PermissionManager permissionManager = pp.getPermissionManager();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        String prefix = ChatColor.translateAlternateColorCodes('&', permissionManager.getPermissionPlayer(p.getUniqueId()).getPrefix());
        if (p.hasPermission("sircraked.color")) {
            event.setFormat(prefix + " §a%1$s§f: §c%2$s");
        } else {
            event.setFormat(prefix + " §a%1$s§f: %2$s");
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!isInGame()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!isInGame()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void feed(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!isInGame()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent event) {
        if (event.getWorld().getName().equals("game")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (Lang.started) {
                if ((event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.SATIATED) || event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.REGEN))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        if (Lang.starting) {
            event.setMotd("§cEmpezando");
        } else if (Lang.started) {
            event.setMotd("§cEn Juego");
        } else {
            event.setMotd("§cEsperando...");
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
                            MessagesController.sendActionBar(player, "§a" + v.getName() + ChatColor.AQUA + " esta a " + ChatColor.RED + realHealth / 2.0F + ChatColor.AQUA + "§4❤");
                        }
                    }
                }
            }
        }
    }

    public boolean isInGame() {
        return Lang.started;
    }
}
