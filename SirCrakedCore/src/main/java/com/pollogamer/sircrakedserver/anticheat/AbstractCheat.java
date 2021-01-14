package com.pollogamer.sircrakedserver.anticheat;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.utils.PlayerUtils;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCheat implements Listener {

    private static final String prefix = "§4§lS§1§LC§EA §7» §a";
    private String cheatName;
    private Map<Player, Integer> alerts;

    public AbstractCheat(String cheatName) {
        this.cheatName = cheatName;
        alerts = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, SirCrakedCore.getCore());
    }

    public void notify(Player player, String extra) {
        addAlert(player);
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.hasPermission("sca.notify")) {
                player1.sendMessage(prefix + player.getName() + " posiblemente usa " + cheatName + " Alerta §c[" + getAlerts(player) + "] " + extra + " §ATPS: " + formatTPS(getTPS()) + " §aPing: " + PlayerUtils.getPlayerPing(player));
            }
        }
    }

    public boolean hasBypass(Player player) {
        return player.hasPermission("sca.bypass." + cheatName);
    }

    public boolean isLag() {
        return getTPS() <= 17;
    }

    public Integer getAlerts(Player player) {
        try {
            return alerts.get(player);
        } catch (Exception e) {
            return 0;
        }
    }

    private void addAlert(Player player) {
        try {
            alerts.put(player, (alerts.get(player) + 1));
        } catch (Exception e) {
            alerts.put(player, 1);
        }
    }

    public static double getTPS() {
        return MinecraftServer.getServer().recentTps[0];
    }

    private static String formatTPS(double tps) {
        return ((tps > 18.0) ? ChatColor.GREEN : (tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED).toString()
                + ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
    }

    public static String getPrefix() {
        return prefix;
    }

    public String getCheatName() {
        return cheatName;
    }

    public Map<Player, Integer> getAlerts() {
        return alerts;
    }
}
