/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Difficulty
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.scoreboard.Scoreboard
 *  org.bukkit.scoreboard.ScoreboardManager
 */
package me.felipefonseca.plugins.manager;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ArenaManager {
    private final Main plugin;
    private int lastSpawnGiven;
    private int spawn;
    private int spawnDM;
    private final Random rand;
    private final List<Location> spawnPoints;
    private final List<Location> dmSpawnPoints;
    private int maxPlayers;
    private int minPlayers;
    private String serverID;
    private Location lobby;
    private final HashMap<Location, Block> blocksPlaced = new HashMap();
    public List<BlockState> blocklist = new ArrayList<BlockState>();

    public ArenaManager(Main plugin) {
        this.plugin = plugin;
        this.spawnPoints = new ArrayList<Location>();
        this.dmSpawnPoints = new ArrayList<Location>();
        this.rand = new Random();
    }

    public void init() {
        this.lobby = Tools.stringToLoc(this.plugin.getArenaConfiguration().getArenaConfig().getString("lobby"));
        this.minPlayers = this.plugin.getArenaConfiguration().getArenaConfig().getInt("min");
        this.maxPlayers = this.plugin.getArenaConfiguration().getArenaConfig().getInt("max");
        this.serverID = this.plugin.getConfig().getString("serverID");
        this.loadSpawns();
        this.prepareWorld(this.plugin.getArenaWorld());
    }

    public void reload() {
        this.loadSpawns();
        this.removeItems();
        this.plugin.getGameManager().init();
        this.plugin.getSkillManager().init();
        for (Player players : this.plugin.getServer().getOnlinePlayers()) {
            players.setScoreboard(this.plugin.getServer().getScoreboardManager().getNewScoreboard());
            players.teleport(this.lobby);
            this.plugin.getPlayerManager().setLobbyPlayer(players);
            this.plugin.getGameManager().addPlayerToGame(players);
        }
    }

    private void prepareWorld(World world) {
        world.setPVP(true);
        world.setDifficulty(Difficulty.HARD);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setStorm(false);
        world.setTime(6000);
    }

    private void loadSpawns() {
        this.spawnPoints.clear();
        this.dmSpawnPoints.clear();
        try {
            int i;
            for (i = 1; i <= this.maxPlayers; ++i) {
                this.spawnPoints.add(Tools.stringToLoc(this.plugin.getArenaConfiguration().getArenaConfig().getString("spawnPoints." + i)));
            }
            for (i = 1; i <= 2; ++i) {
                this.dmSpawnPoints.add(Tools.stringToLoc(this.plugin.getArenaConfiguration().getArenaConfig().getString("dmSpawnPoints." + i)));
            }
        } catch (Exception i) {
            // empty catch block
        }
    }

    public void reloadSpawns() {
        this.spawnPoints.clear();
        try {
            for (int i = 1; i <= this.maxPlayers; ++i) {
                this.spawnPoints.add(Tools.stringToLoc(this.plugin.getArenaConfiguration().getArenaConfig().getString("spawnPoints." + i)));
            }
        } catch (Exception i) {
            // empty catch block
        }
    }

    public void teleport(Player player) {
        int ranint = this.rand.nextInt(this.dmSpawnPoints.size());
        player.teleport(this.dmSpawnPoints.get(ranint));
        this.dmSpawnPoints.remove(ranint);
    }

    public void setSpawnLocation(Player player) {
        String l = Tools.locationToString(player.getLocation());
        ++this.spawn;
        this.plugin.getArenaConfiguration().getArenaConfig().set("spawnPoints." + this.spawn, (Object) l);
        this.plugin.getArenaConfiguration().save();
        this.spawnPoints.clear();
        for (int i = 1; i <= this.spawn; ++i) {
            this.spawnPoints.add(Tools.stringToLoc(this.plugin.getArenaConfiguration().getArenaConfig().getString("spawnPoints." + i)));
        }
        this.plugin.getMessagesController().sendMessage(player, "&7Has puesto el spawn &e&l" + this.spawn);
    }

    public void setDMSpawnLocation(Player player) {
        String l = Tools.locationToString(player.getLocation());
        ++this.spawnDM;
        this.plugin.getArenaConfiguration().getArenaConfig().set("dmSpawnPoints." + this.spawnDM, (Object) l);
        this.plugin.getArenaConfiguration().save();
        this.dmSpawnPoints.clear();
        for (int i = 1; i <= this.spawnDM; ++i) {
            this.dmSpawnPoints.add(Tools.stringToLoc(this.plugin.getArenaConfiguration().getArenaConfig().getString("dmSpawnPoints." + i)));
        }
        this.plugin.getMessagesController().sendMessage(player, "&7Has puesto el deathmatch spawn &e&l" + this.spawnDM);
    }

    public void getNextSpawnPoint(Player player) {
        this.plugin.getServer().getScheduler().runTask(plugin, () -> {
                    player.teleport(this.spawnPoints.get(this.lastSpawnGiven));
                    this.spawnPoints.remove(this.lastSpawnGiven);
                    this.plugin.getGameManager().getPlayers().stream().forEach(p -> {
                                p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
                            }
                    );
                }
        );
        ++this.lastSpawnGiven;
        if (this.lastSpawnGiven >= this.spawnPoints.size()) {
            this.lastSpawnGiven = 0;
        }
    }

    private void removeItems(PlayerDropItemEvent e) {
        this.plugin.getArenaWorld().getEntities().stream().filter(entity -> entity.getType() == EntityType.ARROW || entity.getType() == EntityType.DROPPED_ITEM || entity.getType() == EntityType.EXPERIENCE_ORB).forEach(entity -> {
                    entity.remove();
                }
        );
    }

    public Location getLobby() {
        return this.lobby;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public int getMinPlayers() {
        return this.minPlayers;
    }

    public String getServerID() {
        return this.serverID;
    }

    public boolean isSpawnPointsSet() {
        return this.spawnPoints != null && !this.spawnPoints.isEmpty();
    }

    public int getLastSpawnGiven() {
        return this.lastSpawnGiven;
    }

    public void setLastSpawnGiven(int lastSpawnGiven) {
        this.lastSpawnGiven = lastSpawnGiven;
    }

    public HashMap<Location, Block> getBlocksPlaced() {
        return this.blocksPlaced;
    }

    public List<BlockState> getBlocksBreaked() {
        return this.blocklist;
    }
}

