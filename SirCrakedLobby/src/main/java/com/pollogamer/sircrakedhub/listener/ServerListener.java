package com.pollogamer.sircrakedhub.listener;

import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ServerListener implements Listener {

    @EventHandler
    public void Color(SignChangeEvent event) {
        if (event.getPlayer().hasPermission("sircraked.colorsign")) {
            for (int i = 0; i < 4; i++) {
                event.setLine(i, ChatColor.translateAlternateColorCodes('&', event.getLine(i)));
            }
        }
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void RomperBloque(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (!p.hasPermission("sircraked.romper")) {
            event.setCancelled(true);
            p.sendMessage(Lang.prefix + "§cNo puedes romper eso");
        }
    }

    @EventHandler
    public void DropeoDeItems(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (!p.hasPermission("sircraked.poner")) {
            p.sendMessage(Lang.prefix + "§cNo puedes poner eso");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void Lluvia(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void NivelDeHambre(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onExploxion(ExplosionPrimeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockIgnite(BlockIgniteEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        event.setMotd("§4§lSir§1§lCraked §7§l» HUB");
    }

}
