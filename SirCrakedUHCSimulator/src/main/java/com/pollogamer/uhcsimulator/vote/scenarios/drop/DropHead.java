package com.pollogamer.uhcsimulator.vote.scenarios.drop;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.vote.scenarios.AbstractScenario;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DropHead extends AbstractScenario {

    public DropHead() {
        super("DropHead", 12, new ItemStackBuilder(Material.SKULL_ITEM).setName("&e&lDrop Head").setLore("&eEl jugador muere y se", "&epone su cabeza en 1 valla", "&econ los items dropeados", " ", "&aClick para votar"), "Se dropean los items al suelo", "Drop Head");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (isEnabled()) {
            if (isInGame(event)) {
                Location location = event.getEntity().getLocation();
                location.getBlock().setType(Material.NETHER_FENCE);
                Block skullb = location.getBlock().getRelative(BlockFace.UP);
                skullb.setType(Material.SKULL);
                skullb.setData((byte) 0x1);
                BlockState state = skullb.getState();
                Skull skull = (Skull) state;
                skull.setRotation(BlockFace.NORTH);
                skull.setSkullType(SkullType.PLAYER);
                skull.setOwner(event.getEntity().getName());
                skull.update();
                event.getDrops().add(new ItemStackBuilder(Material.EXP_BOTTLE).setStackAmount(24));
            }
        }
    }

    public boolean isInGame(PlayerDeathEvent event) {
        return Lang.players.contains(event.getEntity());
    }
}
