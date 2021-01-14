package com.pollogamer.builduhc.eventlistener;

import com.github.cheesesoftware.PowerfulPermsAPI.PermissionManager;
import com.github.cheesesoftware.PowerfulPermsAPI.PowerfulPermsPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    PowerfulPermsPlugin pp = (PowerfulPermsPlugin) Bukkit.getPluginManager().getPlugin("PowerfulPerms");
    PermissionManager permissionManager = pp.getPermissionManager();

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
    public void Morir(PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    @EventHandler
    public void P(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("arenapvp.construir")) return;
        if (!(event.getBlock().getType().equals(Material.WOOD) || event.getBlock().getType().equals(Material.COBBLESTONE) || event.getBlock().getType().equals(Material.OBSIDIAN))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getWhoClicked().hasPermission("arenapvp.craftear")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getPlayer().hasPermission("arenapvp.construir")) return;
        Material m = event.getBlock().getType();
        if (m.equals(Material.WORKBENCH) || m.equals(Material.STONE_BUTTON) || m.equals(Material.WOOD_BUTTON)) {
            event.setCancelled(true);
        }
    }

}
