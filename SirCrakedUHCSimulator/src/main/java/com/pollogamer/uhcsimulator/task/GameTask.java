package com.pollogamer.uhcsimulator.task;

import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.extras.Utils;
import com.pollogamer.uhcsimulator.walls.WallBuild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    public static int time = 180;
    private boolean wall1 = false;
    private boolean wall2 = false;
    private boolean wall3 = false;

    public GameTask() {
        runTaskTimer(Principal.getPlugin(), 20, 20);
    }


    @Override
    public void run() {
        checkPlayers();
        if (!wall3) {
            if (time == 180 || time == 150 || time == 120 || time == 90 || time == 60 || time == 30 || time == 15 || time == 10 || time > 0 && time <= 5) {
                Bukkit.broadcastMessage("§6§LUHCSimulator §7» §aEl borde se reducira a " + Lang.nextborder + " x " + Lang.nextborder + " en " + getTime(time));
            }
        }
        if (!wall1) {
            if (time > 0) {
                --time;
            } else {
                Lang.bordersize = 75;
                Lang.nextborder = 50;
                WallBuild.build(75, Bukkit.getWorld("game"));
                wall1 = true;
                time = 120;
                Bukkit.broadcastMessage("§6§lUHCSimulator §7» §aEl borde esta ahora en " + Lang.bordersize + " x " + Lang.bordersize);
                checkPlayersBorder();
            }
        } else if (!wall2) {
            if (time > 0) {
                --time;
            } else {
                Lang.bordersize = 50;
                Lang.nextborder = 25;
                WallBuild.build(50, Bukkit.getWorld("game"));
                wall2 = true;
                time = 120;
                Bukkit.broadcastMessage("§6§lUHCSimulator §7» §aEl borde esta ahora en " + Lang.bordersize + " x " + Lang.bordersize);
                checkPlayersBorder();
            }
        } else if (!wall3) {
            if (time > 0) {
                --time;
            } else {
                Lang.bordersize = 25;
                Lang.nextborder = 25;
                WallBuild.build(25, Bukkit.getWorld("game"));
                wall3 = true;
                time = 0;
                Bukkit.broadcastMessage("§6§lUHCSimulator §7» §aEl borde esta ahora en " + Lang.bordersize + " x " + Lang.bordersize);
                checkPlayersBorder();
            }
        }
    }

    public String getTime(int time) {
        String times = "";
        if (time > 60) {
            if (time == 60) {
                times = "1 minuto!";
            } else {
                String hour = (time / 60) + " minutos";
                if (time % 60 == 0) {
                    times = hour + "!";
                } else {
                    String minutes = " con " + (time % 60) + (time % 60 == 1 ? " segundo!" : " segundos!");
                    times = hour + minutes;
                }
            }
        } else {
            times = time + (time == 1 ? " segundo!" : " segundos!");
        }
        return times;
    }

    public void checkPlayers() {
        if (Lang.players.size() == 1) {
            new WinnerTask();
            cancel();
        }
    }

    public void checkPlayersBorder() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int i = Lang.bordersize + 1;
            int j = Lang.bordersize + 1;
            if ((player.getLocation().getBlockX() >= i || player.getPlayer().getLocation().getBlockX() <= -i || player.getPlayer().getLocation().getBlockZ() >= i || player.getLocation().getBlockZ() <= -i) && player.getWorld().getName().equalsIgnoreCase("game")) {
                if ((player.getLocation().getBlockX() > j || player.getLocation().getBlockX() < -j || player.getLocation().getBlockZ() > j || player.getPlayer().getLocation().getBlockZ() < -j) && player.getPlayer().getWorld().getName().equalsIgnoreCase("game")) {
                    Utils.tpInside(player);
                }
            }
        }
    }
}
