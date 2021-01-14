package com.pollogamer.sircrakedhub.manager;

import com.pollogamer.sircrakedhub.Principal;
import com.pollogamer.sircrakedhub.objects.ListServer;
import com.pollogamer.sircrakedserver.objects.GameServer;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ServerManager {

    public static List<GameServer> otherservers = new ArrayList<>();
    public static List<GameServer> lobbies = new ArrayList<>();

    public ServerManager() {
        registerServers();
    }

    public void registerServers() {
        for (String s : Principal.plugin.getServers().getConfigurationSection("servers.otherservers").getKeys(false)) {
            String ip = getString("servers.otherservers." + s + ".ip");
            String bungeename = getString("servers.otherservers." + s + ".bungeeservername");
            String[] ips = ip.split(":");
            otherservers.add(new GameServer(s, bungeename, ips[0], Integer.parseInt(ips[1])));
        }
        for (String s : Principal.plugin.getServers().getConfigurationSection("servers.listserver").getKeys(false)) {
            ListServer listServer = new ListServer(s);
            for (String ss : Principal.plugin.getServers().getConfigurationSection("servers.listserver." + s + ".servers").getKeys(false)) {
                String name = getString("servers.listserver." + s + ".servers." + ss + ".name");
                String bungeeserver = getString("servers.listserver." + s + ".servers." + ss + ".bungeeservername");
                String ip = getString("servers.listserver." + s + ".servers." + ss + ".ip");
                String[] ipp = ip.split(":");
                listServer.addServer(new GameServer(name, bungeeserver, ipp[0], Integer.parseInt(ipp[1])));
            }
        }
        for (String s : Principal.plugin.getServers().getConfigurationSection("servers.lobbies").getKeys(false)) {
            String name = getString("servers.lobbies." + s + ".name");
            String bungeeserver = getString("servers.lobbies." + s + ".bungeeservername");
            String ip = getString("servers.lobbies." + s + ".ip");
            String[] ipp = ip.split(":");
            lobbies.add(new GameServer(name, bungeeserver, ipp[0], Integer.parseInt(ipp[1])));
        }
    }

    public static GameServer getGameServer(String name) {
        for (GameServer gameServer1 : otherservers) {
            if (gameServer1.getName().equalsIgnoreCase(name)) {
                return gameServer1;
            }
        }
        return null;
    }

    public String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', Principal.plugin.getServers().getString(path));
    }
}
