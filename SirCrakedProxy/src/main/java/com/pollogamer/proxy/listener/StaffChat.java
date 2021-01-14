package com.pollogamer.proxy.listener;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class StaffChat implements Listener {

    @EventHandler
    public void onChat(ChatEvent event) {
        ProxiedPlayer p = (ProxiedPlayer) event.getSender();
        if (!event.getMessage().startsWith("@")) return;
        if (!p.hasPermission("sircraked.staffchat")) return;
        if (p.getServer().getInfo().getName().startsWith("AuthServer")) return;
        event.setCancelled(true);

        for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
            if (all.hasPermission("sircraked.staffchat")) {
                all.sendMessage("§7[§b§lStaff§7] [§b" + p.getServer().getInfo().getName() + "§7]§a " + p.getName() + "§f: §c" + event.getMessage().replace("@", ""));
            }
        }
    }
}
