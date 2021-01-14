/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package me.felipefonseca.plugins.manager;

import com.pollogamer.sircrakedserver.utils.ScoreboardAPI;
import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.manager.enums.FaseState;
import me.felipefonseca.plugins.utils.ItemLoader;
import me.felipefonseca.plugins.utils.MessagesController;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerManager {
    private final Main plugin;
    private ScoreboardAPI board;

    public PlayerManager(Main plugin) {
        this.plugin = plugin;
    }

    public void sendInfo() {
        for (Player players : this.plugin.getServer().getOnlinePlayers()) {
            MessagesController.sendCenteredMessage(players, "                  ");
            MessagesController.sendCenteredMessage(players, "                  ");
            MessagesController.sendCenteredMessage(players, "                  ");
            MessagesController.sendCenteredMessage(players, "&4&lFight&c&lClub");
            MessagesController.sendCenteredMessage(players, "&7El Ganador es &6");
            MessagesController.sendCenteredMessage(players, "&6" + this.plugin.getGameManager().getWinnerName());
            MessagesController.sendCenteredMessage(players, "                  ");
            MessagesController.sendCenteredMessage(players, "                  ");
            MessagesController.sendCenteredMessage(players, "                  ");
        }
    }

    public void sendToServer(Player player) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF("Hub-1");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        player.sendPluginMessage((Plugin) this.plugin, "BungeeCord", b.toByteArray());
    }

    public void setDefaultKit(Player player) {
        this.setCleanPlayer(player, GameMode.SURVIVAL);
        player.getInventory().setHelmet(ItemLoader.getIronHelmet());
        player.getInventory().setChestplate(ItemLoader.getIronChestplate());
        player.getInventory().setLeggings(ItemLoader.getIronLeggins());
        player.getInventory().setBoots(ItemLoader.getIronBoots());
        player.getInventory().setItem(0, ItemLoader.getSword());
        player.getInventory().setItem(1, ItemLoader.getFood());
    }

    public void setUHCKit(Player player) {
        this.setCleanPlayer(player, GameMode.SURVIVAL);
        player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
        player.getInventory().setItem(1, new ItemStack(Material.FISHING_ROD));
        player.getInventory().setItem(2, new ItemStack(Material.BOW));
        player.getInventory().setItem(3, new ItemStack(Material.GOLDEN_APPLE, 4));
        player.getInventory().setItem(4, new ItemStack(Material.GOLDEN_APPLE, 2));
        player.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
        player.getInventory().setItem(8, new ItemStack(Material.COBBLESTONE, 64));
        player.getInventory().setItem(9, new ItemStack(Material.ARROW, 10));
        player.getInventory().setItem(12, new ItemStack(Material.DIAMOND_PICKAXE));
        player.getInventory().setItem(13, new ItemStack(Material.DIAMOND_AXE));
        player.getInventory().setItem(14, new ItemStack(Material.COOKED_BEEF, 5));
    }

    public void addReward(Player player) {
        switch (FaseState.fase) {
            case FASE_1: {
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getXp()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getLapiz()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getEp1()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getEp2()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getEs1()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getPr1()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getPd1()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getEbp1()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getBow()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getArrow()});
                break;
            }
            case FASE_2: {
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getXp2()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getGoldenApple()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getP2()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getS2()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getIh2()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getLp2()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getArrow2()});
                break;
            }
            case FASE_3: {
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getXp()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getLapiz()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getGoldenApple()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getGoldenApple()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getP2()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getEp2()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getBd3()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getS3()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getArrow()});
                player.getInventory().addItem(new ItemStack[]{ItemLoader.getIh2()});
            }
        }
    }

    public void setLobbyPlayer(Player player) {
        this.setupScoreboard(player);
        this.setCleanPlayer(player, GameMode.ADVENTURE);
        player.teleport(this.plugin.getArenaManager().getLobby());
    }

    public void setSpectator(Player player) {
        this.plugin.getGameManager().addSpectator(player);
        this.setCleanPlayer(player, GameMode.SPECTATOR);
        this.plugin.getMessagesController().sendTitle(player, "&c&lEres espectador", "", 5, 10, 5);
        this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                    players.hidePlayer(player);
                }
        );
    }

    public void setCleanPlayer(Player player, GameMode gamemode) {
        player.setMaxHealth(20.0);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setExp(0.0f);
        player.setTotalExperience(0);
        player.setLevel(0);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getActivePotionEffects().stream().forEach(effect -> {
                    player.removePotionEffect(effect.getType());
                }
        );
        player.setGameMode(gamemode);
    }

    public void setupScoreboard(Player player) {
        this.board = new ScoreboardAPI("\u00a74Fight\u00a7cClub ");
        PlayerManager.this.board.add("\u00a77----------- ", 4);
        PlayerManager.this.board.add("\u00a7fJugadores: \u00a7e" + PlayerManager.this.plugin.getGameManager().getPlayers().size(), 3);
        PlayerManager.this.board.add("\u00a77-----------", 2);
        PlayerManager.this.board.add("\u00a77ID: \u00a7f" + PlayerManager.this.plugin.getArenaManager().getServerID(), 1);
        PlayerManager.this.board.add("§eplay.sircraked.com", 0);
        if (player != null) {
            new BukkitRunnable() {
                public void run() {
                    if (player == null) {
                        this.cancel();
                        return;
                    }
                    PlayerManager.this.board.add("\u00a7fJugadores: \u00a7e" + PlayerManager.this.plugin.getGameManager().getPlayers().size(), 3);
                    PlayerManager.this.board.update();
                    PlayerManager.this.board.send(player);
                }
            }.runTaskTimer(this.plugin, 20, 20);
            //new ScoreboardIP(board,player);
        }
    }

    public ScoreboardAPI getBoard() {
        return this.board;
    }

}

