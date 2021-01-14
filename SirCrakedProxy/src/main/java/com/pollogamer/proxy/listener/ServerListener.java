package com.pollogamer.proxy.listener;

import com.pollogamer.proxy.Principal;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class ServerListener implements Listener {

    private static Principal plugin;

    public static String motdc2;
    public static String motdmantenimiento;
    public static String version;

    public ServerListener(Principal plugin) {
        this.plugin = plugin;
        init();
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        if (e.isCancelled()) return;
        if (!(e.getSender() instanceof ProxiedPlayer)) return;
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        if (p.hasPermission("sircraked.bypass")) return;
        String[] args = e.getCursor().split(" ");
        String checked = (args.length > 0 ? args[(args.length - 1)] : e.getCursor()).toLowerCase();

        if (checked.startsWith("/")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockCMDS(ChatEvent event) {
        if ((event.getSender() instanceof ProxiedPlayer)) {
            ProxiedPlayer player = (ProxiedPlayer) event.getSender();
            if (!event.getMessage().startsWith("/")) return;
            String[] command = event.getMessage().toLowerCase().split(" ", 2);

            if ((player.hasPermission("sircraked.bypass." + command[0].substring(1))) || (player.hasPermission("sircraked.bypass"))) {
                return;
            }

            if (Principal.config.getStringList("ComandosBloqueados").contains(command[0].substring(1))) {
                player.sendMessage(Principal.prefix + "No haga eso compa xd");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLogin(ServerConnectEvent event) {
        if (plugin.mantenimiento) {
            if (event.getPlayer().hasPermission("sircraked.mantenimiento.join")) return;
            event.setCancelled(true);
            event.getPlayer().disconnect(plugin.getPrefix() + "Estamos en mantenimiento!");
        }
    }

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        ServerPing ping = event.getResponse();
        if (plugin.mantenimiento) {
            ping.setDescription(motdmantenimiento);
            ServerPing.Protocol version1 = new ServerPing.Protocol(version, ping.getVersion().getProtocol() - 1);
            ping.setVersion(version1);
        } else {
            ping.setDescription(motdc2);
        }
    }

    public static void init() {
        List motdc1 = plugin.config.getStringList("motd");
        motdc2 = String.join("\n", motdc1);
        motdc2 = motdc2.replaceAll("&", "§");
        List motdmantenimiento1 = plugin.config.getStringList("motd_mantenimiento");
        motdmantenimiento = String.join("\n", motdmantenimiento1);
        motdmantenimiento = motdmantenimiento.replaceAll("&", "§");
        version = plugin.config.getString("version_mantenimiento");
        version = version.replaceAll("&", "§");
    }
}
