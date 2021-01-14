package com.pollogamer.sircrakedserver.utils;

import com.pollogamer.SirCrakedCore;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ListIterator;

public class ScoreboardIP {

    ListIterator<String> ip1;
    String next = null;

    public ScoreboardIP(ScoreboardAPI score, Player p) {
        ip1 = Lang.IP.listIterator();
        ip(score, p);
    }

    public void ip(ScoreboardAPI board, Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                }
                if (ip1.hasNext()) {
                    next = ip1.next();
                } else {
                    ip1 = null;
                    ip1 = Lang.IP.listIterator();
                    this.cancel();
                    ip(board, p);
                    return;
                }
                board.add(next, 0);
                board.update();
                board.send(p);
            }
        }.runTaskTimer(SirCrakedCore.getCore(), 80, 2);
    }

}
