package me.felipefonseca.plugins.manager;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.utils.ItemBuilder;
import me.felipefonseca.plugins.utils.MessagesLoader;
import me.felipefonseca.plugins.utils.ScoreboardUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerManager {
    private final Main plugin;
    private final HashMap<Player, Integer> kills = new HashMap();

    public PlayerManager(Main main) {
        this.plugin = main;
    }

    public void setScoreboard(final Player player) {
        ScoreboardUtil scoreboardUtil = new ScoreboardUtil("    §6§lUHC§b§lGames    ");
        new BukkitRunnable() {
            public void run() {
                scoreboardUtil.text(12, "§6§lTu");
                scoreboardUtil.text(11, "§f" + player.getDisplayName());
                scoreboardUtil.text(10, "§c ");
                scoreboardUtil.text(9, "§6§lJugadores");
                scoreboardUtil.text(8, "§f" + PlayerManager.this.plugin.getGameManager().getPlayersInGame().size() + "/" + PlayerManager.this.plugin.getArenaManager().getMaxPlayers());
                scoreboardUtil.text(7, "§3 ");
                scoreboardUtil.text(6, "§6§lAsesinatos");
                scoreboardUtil.text(5, "§f" + PlayerManager.this.getKillsToString(player));
                scoreboardUtil.text(4, "§d ");
                scoreboardUtil.text(3, "§6§lID");
                scoreboardUtil.text(2, "§f" + PlayerManager.this.plugin.getArenaManager().getServer());
                scoreboardUtil.text(1, "§a ");
                scoreboardUtil.text(0, "§bplay.sircraked.com");
                scoreboardUtil.build(player);
            }
        }.runTaskTimer(this.plugin, 0, 20);
    }

    public void loadKit(Player player) {
        this.setCleanPlayer(player, GameMode.SURVIVAL);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
    }

    public void setLobbyPlayer(Player player) {
        this.plugin.getGameManager().addPlayerToGame(player);
        this.setCleanPlayer(player, GameMode.ADVENTURE);
        this.setScoreboard(player);
        player.getInventory().setItem(8, new ItemBuilder(Material.BED).setDisplayName(MessagesLoader.getItemGoToLobby()).build());
    }

    public void setSpectator(Player player) {
        this.setCleanPlayer(player, GameMode.SPECTATOR);
        this.plugin.getGameManager().getSpectators().add(player);
        this.plugin.getMessageSender().sendTitle(player, "§cEres un espectador!", "", 0, 5, 0);
        this.plugin.getGameManager().getPlayersInGame().stream().forEach(player2 -> {
                    player2.hidePlayer(player);
                }
        );
    }

    public void setCleanPlayer(Player player, GameMode gameMode) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setExp(0.0f);
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setFireTicks(0);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(gameMode);
        player.getActivePotionEffects().clear();
    }

    public void sendToServer(Player player) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF("HUB-1");
        } catch (IOException var4_4) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, var4_4);
        }
        player.sendPluginMessage(this.plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
    }

    public int getKillsToString(Player player) {
        if (this.kills.containsKey(player)) {
            return this.kills.get(player);
        }
        return 0;
    }

    public void addKillToPlayer(Player player) {
        int n = 0;
        if (this.kills.containsKey(player)) {
            n = this.kills.get(player);
        }
        this.kills.put(player, n + 1);
    }

    public HashMap<Player, Integer> getKills() {
        return this.kills;
    }

}

