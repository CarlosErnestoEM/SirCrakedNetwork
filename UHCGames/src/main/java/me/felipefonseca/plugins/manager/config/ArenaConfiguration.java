package me.felipefonseca.plugins.manager.config;

import me.felipefonseca.plugins.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ArenaConfiguration {
    private final Main plugin;
    private File arenaFile;
    private final YamlConfiguration arenaConfig;

    public ArenaConfiguration(Main main) {
        this.plugin = main;
        this.arenaConfig = new YamlConfiguration();
    }

    public void init() {
        this.arenaFile = new File(this.plugin.getDataFolder(), "arena.yml");
        if (!this.arenaFile.exists()) {
            this.plugin.saveResource("arena.yml", true);
        }
        try {
            this.arenaConfig.load(this.arenaFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.arenaConfig.save(this.arenaFile);
        } catch (IOException var1_1) {
            // empty catch block
        }
    }

    public void reload() {
        try {
            this.arenaConfig.load(this.arenaFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getArenaConfig() {
        return this.arenaConfig;
    }
}

