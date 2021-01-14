package com.pollogamer.uhc.scoreboard.types;

import com.pollogamer.uhc.Principal;
import com.pollogamer.uhc.scoreboard.ScoreModule;
import org.bukkit.entity.Player;

public class UltraScore extends ScoreModule {

    public UltraScore(Player player) {
        super(player, "&4&lSir&&lCraked &7: &f&lUHC", true, true, 20);
    }

    @Override
    public void buildScore() {
        add("&7&m------------------", 7);
        add("Game Time: %color%" + serializetime(0), 6);
        add("Remaining: %color%" + "0/0", 5);
        add("Kills: %color%" + Principal.getKillsManager().getKills(player), 4);
        add("Team Kills: %color%" + Principal.getKillsManager().getKills(player), 3);
        add("Border: %color%" + getBorderSize(), 2);
        add("&7&m------------------", 1);
        add("%color%play.sircraked.com", 0);
        send(player);
    }
}
