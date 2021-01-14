/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.plugin.messaging.Messenger
 */
package me.felipefonseca.plugins;

import me.felipefonseca.plugins.listener.GameListener;
import me.felipefonseca.plugins.listener.PlayerListener;
import me.felipefonseca.plugins.listener.ServerListener;
import me.felipefonseca.plugins.manager.ArenaManager;
import me.felipefonseca.plugins.manager.GameControllerManager;
import me.felipefonseca.plugins.manager.GameManager;
import me.felipefonseca.plugins.manager.PlayerManager;
import me.felipefonseca.plugins.manager.config.ArenaConfiguration;
import me.felipefonseca.plugins.manager.enums.GameState;
import me.felipefonseca.plugins.utils.ItemLoader;
import me.felipefonseca.plugins.utils.MessagesController;
import me.felipefonseca.plugins.utils.inv.GameController;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main
        extends JavaPlugin {
    private final GameListener gl;
    private final ServerListener sl;
    private final ArenaConfiguration ac;
    private final GameManager gm;
    private final ArenaManager am;
    private final PlayerManager pm;
    private final GameControllerManager sm;
    private final MessagesController msgc;
    private final CommandManager cm;
    private final GameController cs;
    private World arenaWorld;
    private boolean editMode;

    public Main() {
        this.ac = new ArenaConfiguration(this);
        this.gm = new GameManager(this);
        this.am = new ArenaManager(this);
        this.pm = new PlayerManager(this);
        this.sm = new GameControllerManager(this);
        this.msgc = new MessagesController(this);
        this.cm = new CommandManager(this);
        this.gl = new GameListener(this);
        this.sl = new ServerListener(this);
        this.cs = new GameController(this);
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.editMode = this.getConfig().getBoolean("editMode");
        this.arenaWorld = this.getServer().getWorld(this.getConfig().getString("world"));
        this.msgc.init();
        this.cm.init();
        try {
            this.ac.init();
        } catch (IOException | InvalidConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, (Throwable) ex);
        }
        if (this.editMode) {
            this.getLogger().log(Level.INFO, "FFC: Editmode activado correctamente");
        } else if (!this.editMode) {
            ItemLoader.init();
            this.am.init();
            this.gm.init();
            this.gl.init();
            this.sl.init();
            this.sm.init();
            this.cs.init();
            Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
            this.arenaWorld.setAutoSave(false);
            GameState.state = GameState.LOBBY;
            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        }
        this.getLogger().log(Level.INFO, "FFC: Activado correctamente");
    }

    public boolean isEditMode() {
        return this.editMode;
    }

    public World getArenaWorld() {
        return this.arenaWorld;
    }

    public ArenaConfiguration getArenaConfiguration() {
        return this.ac;
    }

    public GameManager getGameManager() {
        return this.gm;
    }

    public ArenaManager getArenaManager() {
        return this.am;
    }

    public PlayerManager getPlayerManager() {
        return this.pm;
    }

    public GameControllerManager getSkillManager() {
        return this.sm;
    }

    public MessagesController getMessagesController() {
        return this.msgc;
    }
}

