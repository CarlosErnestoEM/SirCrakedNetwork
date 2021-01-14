/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Objective
 *  org.bukkit.scoreboard.Score
 *  org.bukkit.scoreboard.Scoreboard
 *  org.bukkit.scoreboard.Team
 */
package me.felipefonseca.plugins.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Iterator;

public class ScoreboardUtil {
    private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private final Objective objective = this.scoreboard.registerNewObjective("dummy", "dummy");
    private boolean reset;
    private final HashMap<Integer, String> scores;

    public ScoreboardUtil(String string) {
        this.objective.setDisplayName(string);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.scores = new HashMap();
    }

    public void setName(String string) {
        this.objective.setDisplayName(string);
    }

    public void text(int n, String string) {
        if (this.scores.containsKey(n)) {
            String string2 = this.scores.get(n);
            if (!string.equalsIgnoreCase(string2)) {
                this.scoreboard.resetScores(string2);
                this.objective.getScore(string).setScore(n);
                this.scores.put(n, string);
            }
        } else {
            this.scores.put(n, string);
            this.objective.getScore(string).setScore(n);
        }
    }

    public void team(String string, String string2) {
        Team team = this.scoreboard.getTeam(string);
        if (team == null) {
            this.scoreboard.registerNewTeam(string);
            this.scoreboard.getTeam(string).setPrefix(string2);
        }
    }

    public void reset() {
        if (!this.isReset()) {
            Iterator<Integer> iterator = this.scores.keySet().iterator();
            while (iterator.hasNext()) {
                int n = iterator.next();
                this.getScoreboard().resetScores(this.scores.get(n));
            }
            this.scores.clear();
            this.setReset(true);
        }
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public void setReset(boolean bl) {
        this.reset = bl;
    }

    public boolean isReset() {
        return this.reset;
    }

    public void build(Player player) {
        player.setScoreboard(this.scoreboard);
    }

    public Team getTeam(String string) {
        return this.scoreboard.getTeam(string);
    }
}

