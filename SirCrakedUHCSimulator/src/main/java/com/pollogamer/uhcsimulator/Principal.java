package com.pollogamer.uhcsimulator;

import com.pollogamer.sircrakedserver.objects.Config;
import com.pollogamer.sircrakedserver.sql.HikariSQLManager;
import com.pollogamer.uhcsimulator.commands.*;
import com.pollogamer.uhcsimulator.extras.Biome;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.extras.Utils;
import com.pollogamer.uhcsimulator.inventory.PlayerInventory;
import com.pollogamer.uhcsimulator.listener.PlayerListener;
import com.pollogamer.uhcsimulator.listener.ServerListener;
import com.pollogamer.uhcsimulator.manager.EloManager;
import com.pollogamer.uhcsimulator.manager.HologramManager;
import com.pollogamer.uhcsimulator.manager.KillManager;
import com.pollogamer.uhcsimulator.objects.UHCPlayerListener;
import com.pollogamer.uhcsimulator.spectator.SpectatorManager;
import com.pollogamer.uhcsimulator.task.EnablePluginTask;
import com.pollogamer.uhcsimulator.vote.scenarios.drop.DropsManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Principal extends JavaPlugin {

    private static Principal plugin;
    private Config config;
    private Config kits;
    private PlayerInventory playerInventory;
    private KillManager killManager;
    private SpectatorManager spectatorManager;
    private HikariSQLManager hikariSQLManager;
    private EloManager eloManager;

    public void onEnable() {
        plugin = this;
        config = new Config(this, "config.yml");
        kits = new Config(this, "kits.yml");
        new Lang();
        registerCommands();
        if (!Lang.setup) {
            new DropsManager();
            playerInventory = new PlayerInventory();
            killManager = new KillManager();
            spectatorManager = new SpectatorManager();
            eloManager = new EloManager();
            hikariSQLManager = new HikariSQLManager("uhcsimulator");
            log("Intentando conectar a la base de datos...");
            hikariSQLManager.openConnection();
            hikariSQLManager.Update("CREATE TABLE IF NOT EXISTS `stats` (`player` VARCHAR(20),`realname` VARCHAR(20),`wins` INT, `played` INT,`kills` INT,`elo` INT)");
            HologramManager.putHolograms();
            registerListeners();
            setWorld();
            createWorld();
        } else {
            log("Please setup the Arena");
        }
    }

    public void onDisable() {
        if (!Lang.setup) {
            HologramManager.removeHolograms();
            log("Se procedera a eliminar el mundo de la partida...");
            World w = Bukkit.getWorld("game");
            Bukkit.unloadWorld(w, false);
            try {
                FileUtils.deleteDirectory(new File("game"));
                Utils.deleteWorld(new File("game"));
            } catch (Exception e) {
                log("An error ocurred deleting the world");
            }
        }
    }

    public void createWorld() {
        Biome.setup(this);
        getLogger().info("Generando el mundo de UHCSimulator...");
        WorldCreator worldCreator = new WorldCreator("game").environment(World.Environment.NORMAL).generateStructures(false).type(WorldType.NORMAL);
        Bukkit.getServer().createWorld(worldCreator);
        World world = Bukkit.getWorld("game");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setStorm(false);
        world.setFullTime(6000);
        killEntities(world);
        Bukkit.getServer().getWorlds().add(world);
        System.gc();
        new EnablePluginTask();
        System.gc();
    }

    public void setWorld() {
        World world = Bukkit.getWorld("lobby");
        world.setAutoSave(false);
        world.setTime(6000);
    }

    public void killEntities(World world) {
        for (Entity entity : world.getEntities()) {
            entity.remove();
        }
    }

    public void log(String text) {
        getLogger().info(text);
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public void registerListeners() {
        log("Registrando listeners...");
        registerListener(new PlayerListener());
        registerListener(new ServerListener());
        registerListener(new UHCPlayerListener());
    }

    public void registerCommand(String command, CommandExecutor commandExecutor) {
        getCommand(command).setExecutor(commandExecutor);
    }

    public void registerCommands() {
        registerCommand("uhcsimulator", new CMDUHCS());
        registerCommand("game", new CMDGame());
        registerCommand("leave", new CMDLeave());
        registerCommand("drops", new CMDDrops());
        registerCommand("reroll", new CMDReroll());
        registerCommand("stats", new CMDStats());
        registerCommand("revive", new CMDRevive());
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    public KillManager getKillManager() {
        return killManager;
    }

    public SpectatorManager getSpectatorManager() {
        return spectatorManager;
    }

    public HikariSQLManager getHikariSQLManager() {
        return hikariSQLManager;
    }

    public EloManager getEloManager() {
        return eloManager;
    }

    public Config getKits() {
        return kits;
    }

    @Override
    public Config getConfig() {
        return config;
    }

    public static Principal getPlugin() {
        return plugin;
    }
}
