package com.pollogamer.uhcsimulator.vote.scenarios.drop;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.extras.Utils;
import com.pollogamer.uhcsimulator.vote.scenarios.AbstractScenario;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DropsTimeBomb extends AbstractScenario {

    public DropsTimeBomb() {
        super("TimeBomb", 14, new ItemStackBuilder(Material.TNT).setName("&aTime Bomb").setLore("&bLos items se meten en el cofre", "&bpero explota en 1 determinado tiempo!", " ", "&eClick para votar!"), "Cofre que se autodestruye alv", "Time Bomb");
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
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        location.getWorld().createExplosion(location, 4);
                        location.getWorld().createExplosion(otherblock.getLocation(), 4);
                        //Bukkit.broadcastMessage("§6")
                    }
                }.runTaskLater(Principal.getPlugin(), 20 * 30);
            }
        }
    }

    @EventHandler
    public void onExplode(BlockExplodeEvent event) {
        if (isEnabled()) {
            event.getBlock().getDrops().clear();
        }
    }


    public boolean isInGame(PlayerDeathEvent event) {
        return Lang.players.contains(event.getEntity());
    }
}
