package me.felipefonseca.plugins.manager;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.utils.ChestItems;
import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaManager {

    private final Main plugin;
    private int maxPlayers;
    private int minPlayers;
    private final Random rand;
    private String server;
    private final List<Location> spawnList;
    private int spawn;
    private Location lobby;
    public int gameTime;
    public int deathMatchTime;

    public ArenaManager(Main main) {
        this.plugin = main;
        this.spawnList = new ArrayList<Location>();
        this.rand = new Random();
    }

    public void init() {
        this.gameTime = this.plugin.getConfig().getInt("gameTime");
        this.deathMatchTime = this.plugin.getConfig().getInt("deathMatchTime");
        this.maxPlayers = this.plugin.getArenaConfiguration().getArenaConfig().getInt("UHCSG.Arena.Max");
        this.minPlayers = this.plugin.getArenaConfiguration().getArenaConfig().getInt("UHCSG.Arena.Min");
        this.server = Bukkit.getServer().getServerName();
        this.lobby = Tools.stringToLoc(this.plugin.getArenaConfiguration().getArenaConfig().getString("UHCSG.Arena.Lobby"), plugin.getConfig().getBoolean("old"));
        this.loadWorld(this.plugin.getWorld());
        this.loadSpawn();
    }

    public void fillChest() {
        for (Chunk chunk : this.plugin.getWorld().getLoadedChunks()) {
            for (BlockState blockState : chunk.getTileEntities()) {
                if (!(blockState instanceof Chest)) continue;
                Chest chest = (Chest) blockState;
                chest.getInventory().clear();
                Inventory inventory = chest.getBlockInventory();
                inventory.clear();
                this.fillChests(inventory);
                chest.update();
            }
        }
    }

    public void fillChests(Inventory inventory) {
        inventory.clear();
        int n = 5;
        int n2 = 10;
        for (int i = 0; i < this.rand.nextInt(n2 - n) + n; ++i) {
            int n3 = this.rand.nextInt(ChestItems.ITEMS.size());
            if (inventory.contains(ChestItems.ITEMS.get(n3))) continue;
            if (ChestItems.ITEMS.get(n3).getType() == Material.DIAMOND) {
                if (this.rand.nextInt(5) != 5) continue;
                inventory.setItem(this.rand.nextInt(27), ChestItems.ITEMS.get(n3));
                continue;
            }
            inventory.setItem(this.rand.nextInt(27), ChestItems.ITEMS.get(n3));
        }
    }

    public void fixPlayer(Location location) {
        location.add(0.0, 0.5, 0.0);
    }

    private void loadWorld(World world) {
        world = this.plugin.getWorld();
        world.setPVP(true);
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setStorm(false);
        world.setDifficulty(Difficulty.NORMAL);
        world.setTime(6000);
    }

    public void teleport(Player player) {
        int n = this.rand.nextInt(this.spawnList.size());
        player.teleport(this.spawnList.get(n));
        this.spawnList.remove(n);
    }

    public void loadSpawn() {
        this.spawnList.clear();
        try {
            for (int i = 1; i <= this.maxPlayers; ++i) {
                this.spawnList.add(Tools.stringToLoc(this.plugin.getArenaConfiguration().getArenaConfig().getString("UHCSG.Arena.Spawn." + i), plugin.getConfig().getBoolean("old")));
            }
        } catch (Exception var1_2) {
            // empty catch block
        }
    }

    public void addSpawn(Player player) {
        String string = Tools.locationToString(player.getLocation());
        ++this.spawn;
        this.plugin.getArenaConfiguration().getArenaConfig().set("UHCSG.Arena.Spawn." + this.spawn, (Object) string);
        this.plugin.getArenaConfiguration().save();
        this.spawnList.clear();
        for (int i = 1; i <= this.spawn; ++i) {
            this.spawnList.add(Tools.stringToLoc(this.plugin.getArenaConfiguration().getArenaConfig().getString("UHCSG.Arena.Spawn." + i), plugin.getConfig().getBoolean("old")));
        }
        this.plugin.getMessageSender().sendMessage(player, "&7Spawn: &e&l" + this.spawn);
    }

    public Location getLobby() {
        return this.lobby;
    }

    public String getServer() {
        return this.server;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public int getMinPlayers() {
        return this.minPlayers;
    }

    public int getDeathMatchTime() {
        return this.deathMatchTime;
    }

    public int getGameTime() {
        return this.gameTime;
    }
}

