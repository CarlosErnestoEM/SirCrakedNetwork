package me.felipefonseca.plugins;

import me.felipefonseca.plugins.listeners.GameListener;
import me.felipefonseca.plugins.listeners.PlayerListener;
import me.felipefonseca.plugins.listeners.ServerListener;
import me.felipefonseca.plugins.manager.ArenaManager;
import me.felipefonseca.plugins.manager.GameManager;
import me.felipefonseca.plugins.manager.GameState;
import me.felipefonseca.plugins.manager.PlayerManager;
import me.felipefonseca.plugins.manager.config.ArenaConfiguration;
import me.felipefonseca.plugins.utils.ChestItemLoader;
import me.felipefonseca.plugins.utils.ChestItems;
import me.felipefonseca.plugins.utils.Messages;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private final ArenaManager am;
    private final GameManager gm;
    private final PlayerManager pm;
    private final GameListener gameListener;
    private final PlayerListener playerListener;
    private final ServerListener serverListener;
    private final ArenaConfiguration ac;
    private final CommandManager cm;
    private final Messages msg;
    private ChestItemLoader cil;
    private World world;
    private boolean editMode;

    public Main() {
        this.am = new ArenaManager(this);
        this.gm = new GameManager(this);
        this.pm = new PlayerManager(this);
        this.gameListener = new GameListener(this);
        this.playerListener = new PlayerListener(this);
        this.serverListener = new ServerListener(this);
        this.ac = new ArenaConfiguration(this);
        this.cm = new CommandManager(this);
        this.msg = new Messages(this);
    }

    public void onEnable() {
        this.saveDefaultConfig();
        try {
            this.ac.init();
        } catch (Exception var1_1) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, var1_1);
        }
        this.world = this.getServer().getWorld(this.getConfig().getString("worldName"));
        this.editMode = this.getConfig().getBoolean("editMode");
        this.cm.init();
        this.msg.init();
        this.getServer().getMessenger().registerOutgoingPluginChannel((Plugin) this, "BungeeCord");
        if (this.editMode) {
            this.getServer().getLogger().log(Level.INFO, "UHCSurvivalGames: EditMode enabled");
        } else {
            ChestItems.initItems();
            this.am.init();
            this.gm.init();
            this.gameListener.registerGameEvents();
            this.playerListener.registerPlayerEvents();
            this.serverListener.registerServerEvents();
            GameState.state = GameState.WAITING;
            this.getServer().getLogger().log(Level.INFO, "UHCGames: Modo arena activado");
            this.world.setAutoSave(false);
        }
    }

    public ArenaManager getArenaManager() {
        return this.am;
    }

    public GameManager getGameManager() {
        return this.gm;
    }

    public PlayerManager getPlayerManager() {
        return this.pm;
    }

    public ArenaConfiguration getArenaConfiguration() {
        return this.ac;
    }

    public Messages getMessageSender() {
        return this.msg;
    }

    public World getWorld() {
        return this.world;
    }

    public boolean getEditMode() {
        return this.editMode;
    }

    public ChestItemLoader getCil() {
        return this.cil;
    }
}

