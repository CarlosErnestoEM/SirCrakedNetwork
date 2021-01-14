package com.pollogamer.uhcsimulator.vote.scenarios.drop;

import com.pollogamer.uhcsimulator.vote.scenarios.AbstractScenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropsManager {

    public static Map<Player, AbstractScenario> votes = new HashMap<>();
    public static List<AbstractScenario> drops = new ArrayList<>();

    public DropsManager() {
        drops.add(new DropNone());
        drops.add(new DropChest());
        drops.add(new DropHead());
        drops.add(new DropsTimeBomb());
    }

    public static void tryVote(Player player, AbstractScenario abstractScenario) {
        if (votes.containsKey(player)) {
            AbstractScenario abstractScenarioo = votes.get(player);
            abstractScenarioo.removeVote();
        }
        vote(player, abstractScenario);
    }

    public static void vote(Player player, AbstractScenario abstractScenario) {
        votes.put(player, abstractScenario);
        player.sendMessage("§6§lUHCSimulator §7» §aHas votado por " + abstractScenario.getPublicname());
        abstractScenario.addVote();
    }

    public static void setWinnerScenario() {
        int tempvotes = 0;
        AbstractScenario scenario = null;
        for (AbstractScenario drop : drops) {
            if (drop.getVotes() > tempvotes) {
                tempvotes = drop.getVotes();
                scenario = drop;
            }
        }
        if (scenario == null) {
            scenario = drops.get(2);
        }
        scenario.enabled = true;
        Bukkit.broadcastMessage("§6§lUHCSimulator §7» §aSe activo el scenario " + scenario.getPublicname());
    }
}
