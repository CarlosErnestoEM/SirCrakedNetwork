package com.pollogamer.uhcsimulator.vote.scenarios.drop;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.uhcsimulator.vote.scenarios.AbstractScenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DropNone extends AbstractScenario {

    public DropNone() {
        super("DropNada", 16, new ItemStackBuilder(Material.BUCKET).setName("&e&lDrop Nada").setLore("§bCuando un jugador muere no", "&bse dropea nada", " ", "&aClick para votar!"), "No se dropea nada", "Drop Nada");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (isEnabled()) {
            event.getDrops().clear();
        }
    }
}
