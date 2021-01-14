/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package me.felipefonseca.plugins.manager.config;

import me.felipefonseca.plugins.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ArenaConfiguration {
    private final Main plugin;
    private File arenaFile;
    private final YamlConfiguration arenaConfig;

    public ArenaConfiguration(Main plugin) {
        this.plugin = plugin;
        this.arenaConfig = new YamlConfiguration();
    }

    public void init() throws IOException, FileNotFoundException, InvalidConfigurationException {
        this.arenaFile = new File(this.plugin.getDataFolder(), "arena.yml");
        if (!this.arenaFile.exists()) {
            this.plugin.saveResource("arena.yml", true);
        }
        this.arenaConfig.load(this.arenaFile);
    }

    public void save() {
        try {
            this.arenaConfig.save(this.arenaFile);
        } catch (IOException var1_1) {
            // empty catch block
        }
    }

    public void reload() throws IOException, FileNotFoundException, InvalidConfigurationException {
        this.arenaConfig.load(this.arenaFile);
    }

    public YamlConfiguration getArenaConfig() {
        return this.arenaConfig;
    }
}

