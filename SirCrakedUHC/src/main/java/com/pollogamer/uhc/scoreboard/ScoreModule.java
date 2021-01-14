package com.pollogamer.uhc.scoreboard;

import com.pollogamer.sircrakedserver.utils.ScoreboardAPI;
import com.pollogamer.uhc.Principal;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class ScoreModule {

    private static ChatColor color = ChatColor.AQUA;
    public Player player;
    private ScoreboardAPI scoreboard;
    public BukkitTask bukkitTask;
    private int refreshticks;

    public ScoreModule(Player player, String title, boolean healthname, boolean healthtab, int refresh) {
        scoreboard = new ScoreboardAPI(title, healthname, healthtab);
        refreshticks = refresh;
        this.player = player;
        buildScore();
    }

    public abstract void buildScore();

    public void add(String text, int score) {
        scoreboard.add(text.replace("%color%", color + ""), score);
    }

    public void send(Player... players) {
        scoreboard.update();
        scoreboard.send(players);
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                } else {
                    scoreboard.update();
                    scoreboard.send(players);
                }
            }
        }.runTaskTimer(Principal.getPlugin(), 0, refreshticks);
    }

    public String serializetime(int time) {
        int temptime = time;
        String hour = "0" + time / 3600 + ":";
        int residous = time % 3600;
        return String.format(hour + "%02d:%02d", residous / 60, residous % 60);
    }

    public int getBorderSize() {
        return 0;
    }

}
