package com.pollogamer.builduhc.manager;

import com.pollogamer.builduhc.Principal;
import com.pollogamer.builduhc.objects.ListServer;
import com.pollogamer.sircrakedserver.objects.GameServer;
import org.bukkit.ChatColor;


public class ServerManager {

    public ServerManager() {
        registerServers();
    }

    public void registerServers() {
        for (String s : Principal.getPlugin().getServers().getConfigurationSection("servers.listserver").getKeys(false)) {
            ListServer listServer = new ListServer(s);
            for (String ss : Principal.getPlugin().getServers().getConfigurationSection("servers.listserver." + s + ".servers").getKeys(false)) {
                String name = getString("servers.listserver." + s + ".servers." + ss + ".name");
                String bungeeserver = getString("servers.listserver." + s + ".servers." + ss + ".bungeeservername");
                String ip = getString("servers.listserver." + s + ".servers." + ss + ".ip");
                String[] ipp = ip.split(":");
                listServer.addServer(new GameServer(name, bungeeserver, ipp[0], Integer.parseInt(ipp[1])));
            }
        }
    }


    public String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', Principal.getPlugin().getServers().getString(path));
    }
}
