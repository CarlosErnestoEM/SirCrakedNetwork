package com.pollogamer.sircrakedhub;

import com.pollogamer.sircrakedhub.comandos.CMDGamemode;
import com.pollogamer.sircrakedhub.comandos.CMDJoin;
import com.pollogamer.sircrakedhub.comandos.CMDLobby;
import com.pollogamer.sircrakedhub.comandos.CMDPets;
import com.pollogamer.sircrakedhub.listener.PlayerListener;
import com.pollogamer.sircrakedhub.listener.ServerListener;
import com.pollogamer.sircrakedhub.manager.ConfigManager;
import com.pollogamer.sircrakedhub.manager.MessageListener;
import com.pollogamer.sircrakedhub.manager.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Principal extends JavaPlugin {

    public static Principal plugin;
    public MessageListener messageListener = new MessageListener();

    public void onEnable() {
        plugin = this;
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", messageListener);
        loadConfig();
        registercmd();
        registerevents();
        new ConfigManager();
        new ServerManager();
    }

    public void registercmd() {
        getCommand("gm").setExecutor(new CMDGamemode());
        getCommand("pets").setExecutor(new CMDPets(this));
        getCommand("sircrakedlobby").setExecutor(new CMDLobby());
        getCommand("join").setExecutor(new CMDJoin());
    }

    public void loadConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        File cfile = new File(getDataFolder(), "config.yml");
        if (!cfile.exists()) {
            getLogger().info("Archivo de configuracion creado");
            saveDefaultConfig();
        }
        getLogger().info("Archivo de configuracion cargado!");
    }

    public void registerevents() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);
    }

    public FileConfiguration getServers() {
        return ConfigManager.servers;
    }

}
