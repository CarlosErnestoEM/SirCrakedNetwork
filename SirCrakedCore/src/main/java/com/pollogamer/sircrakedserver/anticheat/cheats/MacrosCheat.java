package com.pollogamer.sircrakedserver.anticheat.cheats;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.anticheat.AbstractCheat;
import com.pollogamer.sircrakedserver.anticheat.CheatModule;
import com.pollogamer.sircrakedserver.comandos.CMDSc;
import com.pollogamer.sircrakedserver.sanciones.util.Offense;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MacrosCheat extends AbstractCheat {

    private BukkitTask bukkitTask;
    private Map<Player, Integer> clicks = new ConcurrentHashMap<>();
    private int maxClicks = 17;
    private int maxAlerts = 15;

    public MacrosCheat() {
        super("Macros/AutoClicker");
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : clicks.keySet()) {
                    int click = clicks.get(player);

                    if (click >= maxClicks) {
                        MacrosCheat.this.notify(player, "CPS: " + click);
                        SirCrakedCore.getCore().getLogger().info(player.getName() + " realizo " + click + " CPS, VL=" + getAlerts(player));

                        if (getAlerts(player) >= maxAlerts) {
                            if (getTPS() > 17) {
                                CMDSc.punish(CheatModule.commandSender, player.getName(), Offense.HACKS_SEVERITY_1);
                            }

                        }
                    }
                    clicks.remove(player);
                }
                clicks.clear();
            }
        }.runTaskTimer(SirCrakedCore.getCore(), 20, 20);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!hasBypass(player)) {
            if (event.getAction().equals(Action.LEFT_CLICK_AIR)) addClick(event.getPlayer());
        }
    }

    public void addClick(Player player) {
        try {
            clicks.put(player, (clicks.get(player) + 1));
        } catch (Exception e) {
            clicks.put(player, 1);
        }
    }
}
