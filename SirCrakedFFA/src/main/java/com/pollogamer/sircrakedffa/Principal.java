package com.pollogamer.sircrakedffa;

import com.pollogamer.sircrakedffa.comandos.CMDFFA;
import com.pollogamer.sircrakedffa.comandos.CMDOthers;
import com.pollogamer.sircrakedffa.eventlistener.PlayerListener;
import com.pollogamer.sircrakedffa.eventlistener.ServerListener;
import com.pollogamer.sircrakedffa.manager.Lang;
import com.pollogamer.sircrakedffa.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Principal extends JavaPlugin {

    private static Principal plugin;

    public void onEnable() {
        plugin = this;
        loadConfig();
        registercmds();
        registerEvents();
        new PlayerManager();
        new Lang(this);
    }

    private void registercmds() {
        getCommand("ffaadmin").setExecutor(new CMDFFA(this));
        getCommand("ffa").setExecutor(new CMDOthers());
        getCommand("buildffa").setExecutor(new CMDOthers());
        getCommand("comboffa").setExecutor(new CMDOthers());

    }

    private void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ServerListener(), this);
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

    public static Principal getPlugin() {
        return plugin;
    }
}
