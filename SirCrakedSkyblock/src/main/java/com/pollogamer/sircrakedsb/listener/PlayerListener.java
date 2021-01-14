package com.pollogamer.sircrakedsb.listener;

import com.github.cheesesoftware.PowerfulPermsAPI.PermissionManager;
import com.github.cheesesoftware.PowerfulPermsAPI.PowerfulPermsPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
}
