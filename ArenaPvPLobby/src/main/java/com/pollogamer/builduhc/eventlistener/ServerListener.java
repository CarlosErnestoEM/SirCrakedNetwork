package com.pollogamer.builduhc.eventlistener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListener implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        event.setMotd("§4§lSir§1§lCraked §7§l» §aArenaPvP");
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.VOID) {
            return;
        }

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        e.setCancelled(true);
        e.setDamage(0.0D);
        Location spawn = new Location(Bukkit.getWorld("BuildUHC"), 0.311, 101.41, 0.331, -38, 0.4F);
        player.teleport(spawn);
    }
}
