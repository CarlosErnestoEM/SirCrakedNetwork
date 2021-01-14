package com.pollogamer.builduhc;

import com.pollogamer.builduhc.commands.CMDUHCS;
import com.pollogamer.builduhc.eventlistener.PlayerListener;
import com.pollogamer.builduhc.eventlistener.ServerListener;
import com.pollogamer.builduhc.manager.ServerManager;
import com.pollogamer.sircrakedserver.objects.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Principal extends JavaPlugin {

    private static Principal plugin;
    private Config servers;

    public Principal() {
        plugin = this;
    }

    public void onEnable() {
        servers = new Config(this, "servers.yml");
        new ServerManager();
        registerevents();
        registerCommand();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public void registerevents() {
        Bukkit.getServer().getPluginManager().registerEvents(new ServerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void registerCommand() {
        getCommand("uhcsimulator").setExecutor(new CMDUHCS());
    }

    public static Principal getPlugin() {
        return plugin;
    }

    public Config getServers() {
        return servers;
    }
}
