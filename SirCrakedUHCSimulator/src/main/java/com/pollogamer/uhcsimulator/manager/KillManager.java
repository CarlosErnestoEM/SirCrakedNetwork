package com.pollogamer.uhcsimulator.manager;

import com.pollogamer.sircrakedserver.objects.PlayerStorage;
import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class KillManager implements Listener {

    public KillManager() {
        Bukkit.getPluginManager().registerEvents(this, Principal.getPlugin());
        playerStorage = new PlayerStorage();
    }

    private Map<Player, Integer> kills = new HashMap<>();
    private PlayerStorage playerStorage;

    public int getKills(Player player) {
        try {
            return kills.get(player);
        } catch (Exception e) {
            return 0;
        }
    }

    public void addKill(Player player) {
        try {
            kills.put(player, (kills.get(player) + 1));
        } catch (Exception e) {
            kills.put(player, 1);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (Lang.started) {
            Player player = event.getEntity();
            Player killer = event.getEntity().getKiller();
            Principal.getPlugin().getEloManager().removeElo(player, 7);
            playerStorage.saveOldData(player);
            if (killer != null) {
                addKill(killer);
                event.setDeathMessage("§6§lUHCSimulator §6» §a" + player.getName() + " fue violado por " + killer.getName());
                Principal.getPlugin().getEloManager().addElo(killer, 5);
                PacketManager.sendLightning(player.getLocation());
            } else {
                if (Lang.players.contains(player)) {
                    event.setDeathMessage("§6§lUHCSimulator §6» §a" + player.getName() + " murio por pendejo xd");
                } else {
                    event.setDeathMessage(null);
                }
            }
            Lang.players.remove(player);
            Principal.getPlugin().getSpectatorManager().setSpectator(player);
            MySQLManager.uploadToMySQL(player);
        } else {
            event.setDeathMessage(null);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerStorage.tryEraseData(event.getPlayer());
    }

    public PlayerStorage getPlayerStorage() {
        return playerStorage;
    }
}
