package com.pollogamer.uhcsimulator.listener;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.extras.MySQLCheck;
import com.pollogamer.uhcsimulator.extras.Utils;
import com.pollogamer.uhcsimulator.manager.PlayerManager;
import com.pollogamer.uhcsimulator.task.LobbyTask;
import com.pollogamer.uhcsimulator.task.WinnerTask;
import com.pollogamer.uhcsimulator.vote.scenarios.AbstractScenario;
import com.pollogamer.uhcsimulator.vote.scenarios.drop.DropsManager;
import com.pollogamer.uhcsimulator.vote.scenarios.drop.GUIDrops;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class PlayerListener implements Listener {

    public static List<Block> blocks = new ArrayList<>();
    private ItemStackBuilder lobbyitem = new ItemStackBuilder(Material.BED).setName("&aSalir del juego (Click derecho)");
    private ItemStackBuilder voteitem = new ItemStackBuilder(Material.PAPER).setName("&aVotacion");
    private ItemStackBuilder arenapvp = new ItemStackBuilder(Material.DIAMOND_SWORD).setName("&aRegresar a ArenaPvP");

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        new MySQLCheck(player);
        if (!Lang.started) {
            PlayerManager.setCleanPlayer(player);
            Lang.players.add(player);
            player.teleport(Lang.lobby);
            setInventory(player);
            Bukkit.broadcastMessage("§6§lUHCSimulator §7» §a" + player.getName() + " entro al desvergue! §7(§e" + Bukkit.getOnlinePlayers().size() + "§7/§e" + Bukkit.getMaxPlayers() + "§7)");
            PlayerManager.setLobbyScoreboard(player);
            if (!Lang.starting) {
                if (Bukkit.getOnlinePlayers().size() >= Lang.minplayers) {
                    if (!Lang.forcestart) {
                        new LobbyTask();
                        Lang.starting = true;
                    }
                }
            }
        } else {
            Principal.getPlugin().getSpectatorManager().setSpectator(player);
            player.teleport(new Location(Bukkit.getWorld("game"), 0, 100, 0));
            PlayerManager.setGameScoreboard(player);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        if (itemStack != null) {
            String name = itemStack.getItemMeta().getDisplayName();
            if (name != null) {
                if (name.equals(voteitem.getItemMeta().getDisplayName())) {
                    new GUIDrops(player);
                } else if (name.equals(lobbyitem.getItemMeta().getDisplayName())) {
                    PlayerManager.sendToServer(player, "HUB-1");
                } else if (name.equalsIgnoreCase(arenapvp.getItemMeta().getDisplayName())) {
                    PlayerManager.sendToServer(player, "ArenaPvP");
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        if (!Lang.started) {
            Bukkit.broadcastMessage("§6§lUHCSimulator §7» §a" + player.getName() + " salio del desvergue! §7(§e" + (Bukkit.getOnlinePlayers().size() - 1) + "§7/§e" + Bukkit.getMaxPlayers() + "§7)");
            Lang.players.remove(player);
            AbstractScenario abstractScenario = DropsManager.votes.get(player);
            if (abstractScenario != null) {
                abstractScenario.removeVote();
                DropsManager.drops.remove(player);
            }
        } else {
            if (Lang.players.contains(player)) {
                if (WinnerTask.winner == null) {
                    player.setHealth(0);
                    Lang.players.remove(player);
                }
            }
            if (Lang.spectators.contains(player)) {
                Lang.spectators.remove(player);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (!Lang.started) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage2(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (!Lang.started) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        if (!(e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ())) {
            int i = Lang.bordersize + 1;
            int j = Lang.bordersize + 1;
            if ((e.getPlayer().getLocation().getBlockX() >= i || e.getPlayer().getLocation().getBlockX() <= -i || e.getPlayer().getLocation().getBlockZ() >= i || e.getPlayer().getLocation().getBlockZ() <= -i) && e.getPlayer().getWorld().getName().equalsIgnoreCase("game")) {
                final Location l = e.getFrom();
                final Location l2 = e.getTo();
                final double d2 = Math.floor(l.getX());
                final double d3 = Math.floor(l.getZ());
                if (Math.floor(l2.getX()) != d2 || Math.floor(l2.getZ()) != d3) {
                    final Player p = e.getPlayer();
                    if ((e.getPlayer().getLocation().getBlockX() > j || e.getPlayer().getLocation().getBlockX() < -j || e.getPlayer().getLocation().getBlockZ() > j || e.getPlayer().getLocation().getBlockZ() < -j) && e.getPlayer().getWorld().getName().equalsIgnoreCase("game")) {
                        Utils.tpInside(p);
                        /*e.setCancelled(true);
                        Player player = e.getPlayer();
                        player.teleport(e.getFrom());*/
                    }
                }
            }
        }
    }

    public Vector getVector(Player player) {
        org.bukkit.util.Vector vector = player.getVelocity();
        if (player.getLocation().getX() > 0) {

        }
        return vector;
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        blocks.add(event.getBlock());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!event.getBlock().getType().equals(Material.LONG_GRASS) && !event.getBlock().getType().equals(Material.DOUBLE_PLANT)) {
            if (!blocks.contains(event.getBlock())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        if (event.getItem().hasItemMeta()) {
            if (event.getItem().getItemMeta().hasDisplayName()) {
                if (event.getItem().getItemMeta().getDisplayName().equals("§6Golden Head")) {
                    Player player = event.getPlayer();
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 10, 1));
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!Lang.started) {
            event.setCancelled(true);
        }
    }


    public void setInventory(Player player) {
        player.getInventory().setItem(0, voteitem);
        player.getInventory().setItem(8, lobbyitem);
        player.getInventory().setItem(7, arenapvp);
        player.updateInventory();
    }


}
