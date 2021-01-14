package com.pollogamer.uhcsimulator.vote.scenarios.drop;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.vote.scenarios.AbstractScenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GUIDrops extends GUIPage<Principal> {

    private BukkitTask bukkitTask;

    public GUIDrops(Player player) {
        super(Principal.getPlugin(), player, "§aVotacion", 27);
        build();
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                refresh();
            }
        }.runTaskTimer(Principal.getPlugin(), 10, 10);
    }

    @Override
    public void buildPage() {
        for (AbstractScenario drops : DropsManager.drops) {
            addButton(new SimpleButton(drops.getItem().setStackAmount(drops.getVotes())).setAction(((plugin1, player, page) -> DropsManager.tryVote(player, drops))), drops.getSlot());
        }
        fillEmptySlots();
    }

    @Override
    public void destroy() {
        bukkitTask.cancel();
    }

    public void fillEmptySlots() {
        for (int i = 0; i < size; i++) {
            if (menu.getItem(i) == null) {
                addButton(new SimpleButton(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setName("").setStackData((short) 15)), i);
            }
        }
    }

}
