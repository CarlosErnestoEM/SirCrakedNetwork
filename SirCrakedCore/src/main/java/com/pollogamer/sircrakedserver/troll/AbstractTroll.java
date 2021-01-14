package com.pollogamer.sircrakedserver.troll;

import com.minebone.itemstack.ItemStackBuilder;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class AbstractTroll {

    public int slot;
    public String name;
    public ItemStackBuilder itemStackBuilder;
    public boolean gui = false;

    public AbstractTroll(int slot, String trollname, ItemStackBuilder itemStackBuilder, boolean gui) {
        this.slot = slot;
        this.name = trollname;
        this.itemStackBuilder = itemStackBuilder;
        this.gui = gui;
    }

    public abstract void action(Player player, Player trolled);

    public void playEffect(Effect effect, Location location, int times) {
        int i = 0;
        while (i < times) {
            location.getWorld().playEffect(location, effect, 2);
            i++;
        }
    }

    public void playEffectBlock(Location location, int times, int idblock) {
        int i = 0;
        while (i < times) {
            location.getWorld().playEffect(location, Effect.STEP_SOUND, idblock);
            i++;
        }
    }
}
