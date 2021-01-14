package com.pollogamer.sircrakedsb;

import com.pollogamer.sircrakedsb.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Principal extends JavaPlugin {

    public void onEnable() {
        registerListener(new PlayerListener());
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }
}
