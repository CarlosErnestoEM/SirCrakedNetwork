package com.pollogamer.uhcsimulator.task;

import com.pollogamer.sircrakedserver.utils.MessagesController;
import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.manager.PlayerManager;
import com.pollogamer.uhcsimulator.walls.WallBuild;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PreGameTask extends BukkitRunnable implements Listener {

    public static List<Player> players = new ArrayList<>();
    private List<Horse> horses = new ArrayList<>();
    private int time = 18;
    public static boolean enabled = false;

    public PreGameTask() {
        Lang.starting = false;
        enabled = true;
        for (int i = 0; i < Lang.players.size(); i++) {
            Player player = Lang.players.get(i);
            //UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    Location location = WallBuild.getRandomLocation();
                    player.teleport(location);
                    Horse horse = createPig(location, player);
                    players.add(player);
                    PlayerManager.setGameScoreboard(player);
                    Principal.getPlugin().getPlayerInventory().setAllPlayer(player);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10000, 254));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 10000, 128));
                    horse.setPassenger(player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            horse.setPassenger(player);
                        }
                    }.runTaskLater(Principal.getPlugin(), 10);
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        MessagesController.sendTitle(player1, "§cTeletransportando...", "§a" + finalI + "/" + Lang.players.size(), 0, 1, 0);
                    }
                    if (finalI == (Lang.players.size() - 1)) {
                        for (Player player1 : Lang.players) {
                            MessagesController.sendTitle(player1, "", "§aEl juego iniciara en 1 momento! Espera :v", 20, 10, 20);
                        }
                    }
                }
            }.runTaskLater(Principal.getPlugin(), i);
        }
        Bukkit.getPluginManager().registerEvents(this, Principal.getPlugin());
        runTaskTimer(Principal.getPlugin(), 10, 20);
    }


    @Override
    public void run() {
        if (time > 0) {
            if (time == 11) {
                Bukkit.broadcastMessage("§6§lUHCSimulator §7» §aSi eres VIP puedes cambiar tu kit usando /reroll");
            }
            if (time == 10 || time > 0 && time <= 5) {
                Bukkit.broadcastMessage("§6§lUHCSimulator §7» §aSeras liberado en " + time + (time == 1 ? " segundo!" : " segundos!"));
            }
            --time;
        } else {
            Bukkit.broadcastMessage("§6§lUHCSimulator §7» §aEl juego ha sido iniciado! Wena suerte joven");
            for (Player player : Lang.players) {
                player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            }
            enabled = false;
            players.clear();
            removePigs();
            HandlerList.unregisterAll(this);
            new GameTask();
            cancel();
        }
    }

    public Horse createPig(Location location, Player player) {
        Horse pig = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
        pig.setBreed(true);
        pig.setOwner(player);
        pig.setTamed(true);
        pig.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 10000, 1));
        pig.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10000, 254));
        horses.add(pig);
        return pig;
    }

    private void removePigs() {
        for (Horse horses : horses) {
            horses.remove();
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onShot(ProjectileLaunchEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage2(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace2(PlayerBucketEmptyEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void chairLeft(VehicleExitEvent event) {
        if (event.getExited() != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        event.setCancelled(true);
    }

}
