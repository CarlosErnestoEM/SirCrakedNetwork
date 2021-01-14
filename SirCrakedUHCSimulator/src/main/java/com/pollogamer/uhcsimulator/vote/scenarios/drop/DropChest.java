package com.pollogamer.uhcsimulator.vote.scenarios.drop;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.extras.Utils;
import com.pollogamer.uhcsimulator.vote.scenarios.AbstractScenario;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DropChest extends AbstractScenario {

    public DropChest() {
        super("DropChest", 10, new ItemStackBuilder(Material.CHEST).setName("&e&lDrop Chest").setLore("&eCuando el jugador muere", "&ese ponen todos sus items en 1 cofre", " ", "&aClick para votar!"), "Todos los items se meten al cofre", "Drop Chest");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (isEnabled()) {
            if (isInGame(event)) {
                Location location = event.getEntity().getLocation();
                location.getBlock().setType(Material.CHEST);
                Block otherblock = location.getBlock().getRelative(BlockFace.NORTH);
                otherblock.setType(Material.CHEST);
                Chest chest = (Chest) location.getBlock().getState();
                chest.getInventory().setContents(Utils.concatenateArray(event.getEntity().getInventory().getContents(), event.getEntity().getInventory().getArmorContents()));
                chest.getInventory().addItem(new ItemStackBuilder(Material.EXP_BOTTLE).setStackAmount(24));
                Utils.addRelativeBlocks(location.getBlock());
                Utils.addRelativeBlocks(otherblock);
                event.getDrops().clear();
            }
        }
    }


    public boolean isInGame(PlayerDeathEvent event) {
        return Lang.players.contains(event.getEntity());
    }

}
