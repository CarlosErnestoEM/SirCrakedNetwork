package com.pollogamer.sircrakedserver.objects;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Config extends YamlConfiguration {

    private JavaPlugin plugin;
    private File configFile;
    private String filename;

    public Config(JavaPlugin plugin, String file) {
        this.plugin = plugin;
        this.configFile = new File(this.plugin.getDataFolder(), file);
        this.filename = file;
        saveDefault();
    }

    public void reload() {
        try {
            super.load(this.configFile);
            plugin.getLogger().info("Archivo " + filename + " recargado!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            super.save(this.configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        if (!configFile.exists()) {
            plugin.saveResource(filename, false);
            plugin.getLogger().info("Archivo " + filename + " creado!");
            reload();
        } else {
            reload();
        }
    }

}
