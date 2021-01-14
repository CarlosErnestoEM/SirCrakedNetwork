package com.pollogamer.uhcsimulator.manager;

import com.pollogamer.sircrakedserver.utils.MessagesController;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class EloManager {

    private Map<Player, Integer> elo = new HashMap<>();

    public int getElo(Player player) {
        try {
            return elo.get(player);
        } catch (Exception e) {
            return 0;
        }
    }

    public void addElo(Player player, int elo) {
        try {
            this.elo.put(player, (this.elo.get(player) + elo));
        } catch (Exception e) {
            this.elo.put(player, elo);
        }
        MessagesController.sendActionBar(player, "§e" + elo + " de elo!");
    }

    public void removeElo(Player player, int elo) {
        try {
            this.elo.put(player, (this.elo.get(player) - elo));
        } catch (Exception e) {
            this.elo.put(player, -elo);
        }
        MessagesController.sendActionBar(player, "§e-" + elo + " de elo!");
    }
}
